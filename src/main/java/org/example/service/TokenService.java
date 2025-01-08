package org.example.service;

import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import org.example.dto.jwt.JwtDTO;
import org.example.exception.ExceptionUtil;
import org.example.util.JwtUtil;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TokenService {

    public JwtDTO parseToken(String token) {
        return JwtUtil.decode(token);
    }

    public JwtDTO parseTokenWithTryCatch(String token) {
        try {
            return JwtUtil.decode(token);
        } catch (JwtException e) {
            throw ExceptionUtil.throwCustomIllegalArgumentException("JWT validation error.");
        }
    }

}
