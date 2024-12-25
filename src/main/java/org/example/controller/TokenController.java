package org.example.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.dto.jwt.JwtDTO;
import org.example.service.TokenService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequestMapping("/api/tokens")

public class TokenController {

    TokenService tokenService;

    @GetMapping("/generate")
    public ResponseEntity<String> generateToken(@RequestParam String username, @RequestParam String role) {
        return ResponseEntity.ok(tokenService.generateToken(username, role));
    }

    @GetMapping("/parse/{token}")
    public ResponseEntity<JwtDTO> parseToken(@PathVariable("token") String token) {
        return ResponseEntity.ok(tokenService.parseToken(token));
    }

    @GetMapping("/parse/catch/{token}")
    public ResponseEntity<?> parseTokenWithTryCatch(@PathVariable("token") String token) {
        return ResponseEntity.ok(tokenService.parseTokenWithTryCatch(token));
    }
}


