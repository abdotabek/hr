package org.example.service;

import org.example.dto.LoginVM;
import org.example.dto.employee.EmployeeDTO;
import org.example.dto.jwt.AuthRequestDTO;
import org.example.dto.jwt.AuthResponseDTO;
import org.example.dto.jwt.LoginVMDTO;
import org.example.dto.jwt.TokenDTO;
import org.springframework.http.ResponseEntity;

public interface AuthService {

    EmployeeDTO registration(EmployeeDTO employeeDTO);

    AuthResponseDTO authorization(AuthRequestDTO authRequestDTO);

    TokenDTO getAccessToken(TokenDTO tokenDTO);

    ResponseEntity<LoginVMDTO> login(LoginVM loginVM);
}
