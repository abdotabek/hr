package org.example.controller.api;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import org.example.config.doc.DocMethodAuth;
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


@RequestMapping("/api/auths")
public interface AuthControllerApi {

    @DocMethodAuth(
        summary = "Create Registration",
        responseCode = "200",
        description = "Operation success",
        content = @Content(schema = @Schema(implementation = EmployeeDTO.class))
    )
    @PostMapping("/registration")
    ResponseEntity<Result<EmployeeDTO>> creteRegistration(@RequestBody final EmployeeDTO employeeDTO);

    @DocMethodAuth(
        summary = "Authorization",
        responseCode = "200",
        description = "Operation success",
        content = @Content(schema = @Schema(implementation = AuthResponseDTO.class))
    )
    @PostMapping("/authorization")
    ResponseEntity<Result<AuthResponseDTO>> authorization(@RequestBody final AuthRequestDTO authRequestDTO);

    @DocMethodAuth(
        summary = "Refresh token",
        responseCode = "200",
        description = "Operation success",
        content = @Content(schema = @Schema(implementation = TokenDTO.class))
    )
    @PostMapping("/refresh-token")
    ResponseEntity<Result<TokenDTO>> refreshToken(@RequestBody TokenDTO tokenDTO);

    @DocMethodAuth(
        summary = "Login",
        responseCode = "200",
        description = "Operation success",
        content = @Content(schema = @Schema(implementation = LoginVMDTO.class))
    )
    @PostMapping("/login")
    ResponseEntity<LoginVMDTO> login(@Valid @RequestBody LoginVM loginVM);
}
