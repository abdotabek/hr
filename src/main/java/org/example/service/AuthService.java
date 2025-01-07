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
import org.example.entity.redis.BlockList;
import org.example.exception.ExceptionUtil;
import org.example.repository.BlockListRepository;
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
    BlockListRepository blockListRepository;


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
            {
                if (authentication.isAuthenticated()) {
                    CustomUserDetails employee = (CustomUserDetails) authentication.getPrincipal();

                    if (GeneralStatus.BLOCK == employee.getStatus()) {
                        BlockList blockList = new BlockList();
                        blockList.setId(employee.getPhone());
                        blockList.setEmployeeId(employee.getEmployeeId());
                        blockList.setAccessToken(JwtUtil.encode(employee.getPhone(), employee.getRole().name()));

                        blockListRepository.save(blockList);

                        throw ExceptionUtil.throwConflictException("Employee is block");
                    }
                    String accessToken = JwtUtil.encode(employee.getPhone(), employee.getRole().name());
                    String refreshToken = JwtUtil.generationRefreshToken(employee.getPhone(), employee.getRole().name());

                    AuthResponseDTO response = new AuthResponseDTO();
                    response.setAccessToken(accessToken);
                    response.setRefreshToken(refreshToken);
                    return response;
                }
            }
        } catch (BadCredentialsException e) {
            throw ExceptionUtil.throwEmployeeNotFoundException("Phone or password wrong");
        }
        throw ExceptionUtil.throwEmployeeNotFoundException("Phone or password wrong");
    }

    public TokenDTO getAccessToken(TokenDTO tokenDTO) {
        if (JwtUtil.isValid(tokenDTO.getRefreshToken()) && !JwtUtil.isTokenExpired(tokenDTO.getRefreshToken())) {
            JwtDTO jwtDTO = JwtUtil.decode(tokenDTO.getRefreshToken());

            Optional<BlockList> blockList = blockListRepository.findByEmployeeId(Long.valueOf(jwtDTO.getUserName()));
            if (blockList.isPresent()) {
                throw ExceptionUtil.throwConflictException("Employee token blocked");
            }
            String accessToken = JwtUtil.encode(jwtDTO.getUserName(), jwtDTO.getRole());
            String refreshToken = JwtUtil.generationRefreshToken(jwtDTO.getUserName(), jwtDTO.getRole());

            TokenDTO response = new TokenDTO();
            response.setAccessToken(accessToken);
            response.setRefreshToken(refreshToken);
            response.setExpaired(System.currentTimeMillis() + JwtUtil.accessTokenLiveTime);
            response.setType("Bearer ");
            return response;
        }
        throw ExceptionUtil.throwCustomIllegalArgumentException("Invalid or expired refresh token");

    }

}
