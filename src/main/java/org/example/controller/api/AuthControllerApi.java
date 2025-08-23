package org.example.controller.api;

import jakarta.validation.Valid;
import org.example.dto.LoginVM;
import org.example.dto.Result;
import org.example.dto.employee.EmployeeDTO;
import org.example.dto.jwt.AuthRequestDTO;
import org.example.dto.jwt.AuthResponseDTO;
import org.example.dto.jwt.LoginVMDTO;
import org.example.dto.jwt.TokenDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auths")
public interface AuthControllerApi {

    @PostMapping("/registration")
    ResponseEntity<Result<EmployeeDTO>> creteRegistration(@RequestBody final EmployeeDTO employeeDTO);

    @PostMapping("/authorization")
    ResponseEntity<Result<AuthResponseDTO>> authorization(@RequestBody final AuthRequestDTO authRequestDTO);

    @PostMapping("/refresh-token")
    ResponseEntity<Result<TokenDTO>> refreshToken(@RequestBody TokenDTO tokenDTO);

    @PostMapping("/login")
    ResponseEntity<LoginVMDTO> login(@Valid @RequestBody LoginVM loginVM);
}
