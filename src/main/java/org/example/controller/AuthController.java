package org.example.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.dto.employee.EmployeeDTO;
import org.example.dto.jwt.AuthRequestDTO;
import org.example.dto.jwt.AuthResponseDTO;
import org.example.dto.jwt.TokenDTO;
import org.example.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auths")
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class AuthController {
    AuthService authService;

    @PostMapping("/registration")
    public ResponseEntity<EmployeeDTO> creteRegistration(@RequestBody EmployeeDTO employeeDTO) {
        return ResponseEntity.ok(authService.registration(employeeDTO));
    }

    @PostMapping("/authorization")
    public ResponseEntity<AuthResponseDTO> authorization(@RequestBody AuthRequestDTO authRequestDTO) {
        return ResponseEntity.ok(authService.authorization(authRequestDTO));
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<TokenDTO> refreshToken(@RequestBody TokenDTO tokenDTO) {
        return ResponseEntity.ok(authService.getAccessToken(tokenDTO));

    }
}
