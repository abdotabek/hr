package org.example.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.dto.LoginVM;
import org.example.dto.Result;
import org.example.dto.employee.EmployeeDTO;
import org.example.dto.jwt.AuthRequestDTO;
import org.example.dto.jwt.AuthResponseDTO;
import org.example.dto.jwt.LoginVMDTO;
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
public class AuthController {

    private final AuthService authService;

    @PostMapping("/registration")
    public ResponseEntity<Result<EmployeeDTO>> creteRegistration(@RequestBody final EmployeeDTO employeeDTO) {
        return ResponseEntity.ok(Result.success(authService.registration(employeeDTO)));
    }

    @PostMapping("/authorization")
    public ResponseEntity<Result<AuthResponseDTO>> authorization(@RequestBody final AuthRequestDTO authRequestDTO) {
        return ResponseEntity.ok(Result.success(authService.authorization(authRequestDTO)));
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<Result<TokenDTO>> refreshToken(@RequestBody TokenDTO tokenDTO) {
        return ResponseEntity.ok(Result.success(authService.getAccessToken(tokenDTO)));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginVMDTO> login(@Valid @RequestBody LoginVM loginVM) {
        return authService.login(loginVM);
    }
}
