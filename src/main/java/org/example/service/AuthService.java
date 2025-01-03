package org.example.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.config.CustomUserDetails;
import org.example.dto.employee.EmployeeDTO;
import org.example.dto.enums.GeneralStatus;
import org.example.dto.jwt.AuthRequestDTO;
import org.example.dto.jwt.AuthResponseDTO;
import org.example.dto.jwt.JwtDTO;
import org.example.dto.jwt.TokenDTO;
import org.example.entity.Employee;
import org.example.entity.redis.TokenStore;
import org.example.exception.ExceptionUtil;
import org.example.repository.EmployeeRepository;
import org.example.repository.TokenStoreRepository;
import org.example.repository.mapper.EmployeeMapper;
import org.example.util.JwtUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class AuthService {
    TokenStoreRepository tokenStoreRepository;
    AuthenticationManager authenticationManager;
    EmployeeRepository employeeRepository;
    BCryptPasswordEncoder cryptPasswordEncoder;
    EmployeeMapper mapper;


    public EmployeeDTO registration(EmployeeDTO employeeDTO) {
        if (employeeRepository.existsEmployeeByPhoneNumber(employeeDTO.getPhoneNumber())) {
            throw ExceptionUtil.throwConflictException("Employee with this phone number already exists");
        }
        Employee employee = new Employee();
        employee.setFirstName(employeeDTO.getFirstName());
        employee.setLastName(employeeDTO.getLastName());
        employee.setPhoneNumber(employeeDTO.getPhoneNumber());
        employee.setEmail(employeeDTO.getEmail());
        employee.setCompanyId(employeeDTO.getCompanyId());
        employee.setBranchId(employeeDTO.getBranchId());
        employee.setDepartmentId(employeeDTO.getDepartmentId());
        employee.setPositionId(employeeDTO.getPositionId());
        employee.setPassword(cryptPasswordEncoder.encode(employeeDTO.getPassword()));
        employee.setRole(employeeDTO.getRole());
        return mapper.toDTO(employeeRepository.save(employee));
    }

    public AuthResponseDTO authorization(AuthRequestDTO authRequestDTO) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequestDTO.getPhone(), authRequestDTO.getPassword()));

            if (authentication.isAuthenticated()) {
                CustomUserDetails employee = (CustomUserDetails) authentication.getPrincipal();

                if (GeneralStatus.BLOCK == employee.getStatus()) {
                    throw ExceptionUtil.throwConflictException("Employee is blocked");
                }
                String accessToken = JwtUtil.encode(employee.getPhone(), employee.getRole().name());
                String refreshToken = JwtUtil.generationRefreshToken(employee.getPhone(), employee.getRole().name());

                TokenStore tokenStore = new TokenStore();
                tokenStore.setId(employee.getPhone());   //использую телефон в качестве ключа
                tokenStore.setEmployeeId(employee.getEmployeeId());
                tokenStore.setAccessToken(accessToken);
                tokenStore.setRefreshToken(refreshToken);
                tokenStoreRepository.save(tokenStore);

                AuthResponseDTO response = new AuthResponseDTO();
                response.setAccessToken(accessToken);
                response.setRefreshToken(refreshToken);
                return response;
            }
        } catch (BadCredentialsException e) {
            throw ExceptionUtil.throwEmployeeNotFoundException("Phone or password wrong");
        }
        throw ExceptionUtil.throwEmployeeNotFoundException("Phone or password wrong");
    }

    public TokenDTO getAccessToken(TokenDTO tokenDTO) {

        if (JwtUtil.isValid(tokenDTO.getRefreshToken()) && !JwtUtil.isTokenExpired(tokenDTO.getRefreshToken())) {
            JwtDTO jwtDTO = JwtUtil.decode(tokenDTO.getRefreshToken());

            TokenStore tokenStore = tokenStoreRepository.findById(jwtDTO.getUserName()).orElseThrow(() ->
                    ExceptionUtil.throwNotFoundException("Refresh token not found"));

            if (!tokenStore.getRefreshToken().equals(tokenDTO.getRefreshToken())) {
                throw ExceptionUtil.throwCustomIllegalArgumentException("Invalid refresh token");
            }
            Optional<Employee> optional = employeeRepository.findByPhoneNumber(jwtDTO.getUserName());

            if (optional.isPresent()) {
                Employee employee = optional.get();

                if (GeneralStatus.BLOCK == employee.getStatus()) {
                    throw ExceptionUtil.throwConflictException("Employee is blocked");
                }
                String accessToken = JwtUtil.encode(employee.getPhoneNumber(), employee.getRole().name());
                String refreshToken = JwtUtil.generationRefreshToken(employee.getPhoneNumber(), employee.getRole().name());

                tokenStore.setAccessToken(accessToken);
                tokenStore.setRefreshToken(refreshToken);
                tokenStoreRepository.save(tokenStore);

                TokenDTO response = new TokenDTO();
                response.setAccessToken(accessToken);
                response.setRefreshToken(refreshToken);
                response.setExpaired(System.currentTimeMillis() + JwtUtil.accessTokenLiveTime);
                response.setType("Bearer");
                return response;

            }
        }
        throw ExceptionUtil.throwCustomIllegalArgumentException("Invalid or expired refresh token");
    }

}
