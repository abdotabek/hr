package org.example.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.config.CustomUserDetails;
import org.example.config.JwtAuthenticationFilter;
import org.example.dto.LoginVM;
import org.example.dto.employee.EmployeeDTO;
import org.example.dto.enums.GeneralStatus;
import org.example.dto.jwt.AuthRequestDTO;
import org.example.dto.jwt.AuthResponseDTO;
import org.example.dto.jwt.JwtDTO;
import org.example.dto.jwt.LoginVMDTO;
import org.example.dto.jwt.TokenDTO;
import org.example.entity.Employee;
import org.example.exception.ExceptionUtil;
import org.example.repository.BlockListRepository;
import org.example.repository.EmployeeRepository;
import org.example.repository.mapper.EmployeeMapper;
import org.example.util.JwtUtil;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class AuthService {

    AuthenticationManager authenticationManager;
    EmployeeRepository employeeRepository;
    BCryptPasswordEncoder cryptPasswordEncoder;
    EmployeeMapper mapper;
    BlockListRepository blockListRepository;
    JwtAuthenticationFilter authenticationFilter;
    private final EmployeeService employeeService;

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

                        throw ExceptionUtil.throwConflictException("Employee is block");
                    }
                    JwtDTO jwtDTO = new JwtDTO(employee.getId(), employee.getPhone(), employee.getRole().name());
                    String accessToken = JwtUtil.generateAccessToken(jwtDTO);
                    String refreshToken = JwtUtil.generationRefreshToken(jwtDTO);

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
        if (authenticationFilter.isValid(tokenDTO.getRefreshToken()) && !JwtUtil.isTokenExpired(tokenDTO.getRefreshToken())) {
            JwtDTO jwtDTO = JwtUtil.decode(tokenDTO.getRefreshToken());

            if (blockListRepository.existsById(jwtDTO.getId())) {
                throw ExceptionUtil.throwConflictException("Employee token is blocked");
            }
            String accessToken = JwtUtil.generateAccessToken(jwtDTO);
            String refreshToken = JwtUtil.generationRefreshToken(jwtDTO);

            TokenDTO response = new TokenDTO();
            response.setAccessToken(accessToken);
            response.setRefreshToken(refreshToken);
            response.setExpired(System.currentTimeMillis() + JwtUtil.accessTokenLiveTime);
            response.setType("Bearer ");
            return response;
        }
        throw ExceptionUtil.throwCustomIllegalArgumentException("Invalid or expired refresh token");

    }

    public ResponseEntity<LoginVMDTO> login(LoginVM loginVM) {
        try {
            UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginVM.getUsername(), loginVM.getPassword());

            Authentication authentication = authenticationManager.authenticate(authenticationToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);

            CustomUserDetails customUser = (CustomUserDetails) authentication.getPrincipal();

            JwtDTO jwtDTO = new JwtDTO(
                customUser.getId(),
                customUser.getPhone(),
                customUser.getRole().name(),
                null
            );

            String accessToken = JwtUtil.generateAccessToken(jwtDTO);
            String refreshToken = JwtUtil.generationRefreshToken(jwtDTO);

            LoginVMDTO tokenDTO = new LoginVMDTO();
            tokenDTO.setAccessToken(accessToken);
            tokenDTO.setRefreshToken(refreshToken);

            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken);

            employeeService.authenticationSuccessHandler(loginVM);
            return new ResponseEntity<>(tokenDTO, headers, HttpStatus.OK);
        } catch (Exception e) {
            employeeService.authenticationFailureHandler(loginVM, e);
            throw e;
        }
    }
}
