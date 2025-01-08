package org.example.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.example.dto.jwt.JwtDTO;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtUtil {
    public static final Long accessTokenLiveTime = 3600 * 8 * 1000 + 1L;
    private static final Long refreshTokenLiveTime = 3600 * 24 * 15 * 1000 + 1L;
    private static final String secretKey = "veryLongSecretlasharamazgillattayevaxmojonjinnijonsurbetbekkiydirhonuxlatdibekloxovdangasabekochkozjonduxovmashaynikmaydagapchishularnioqiganbolsangizgapyoqaniqsizmazgi";


    public static String generateToken(Long id, String username, String role, String tokenType) {
        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("tokenType", tokenType);
        extraClaims.put(JwtClaims.USERNAME, username);
        extraClaims.put(JwtClaims.ROLE, role);
        extraClaims.put("id", id);
        Long tokenLifeTime = ("access".equals(tokenType)) ? accessTokenLiveTime : refreshTokenLiveTime;
        return Jwts
                .builder()
                .claims(extraClaims)
                .subject(username)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + tokenLifeTime))
                .signWith(getSignInKey())
                .compact();
    }

    public static String generateAccessToken(JwtDTO jwtDTO) {
        return generateToken(jwtDTO.getId(), jwtDTO.getUserName(), jwtDTO.getRole(), "access");
    }

    public static String generationRefreshToken(JwtDTO jwtDTO) {
        return generateToken(jwtDTO.getId(), jwtDTO.getUserName(), jwtDTO.getRole(), "refresh");
    }

    public static boolean isTokenExpired(String token) {
        try {
            Date expirationDate = Jwts
                    .parser()
                    .verifyWith(getSignInKey())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload()
                    .getExpiration();
            return expirationDate.before(new Date());
        } catch (JwtException e) {
            return true;
        }
    }

    public static JwtDTO decode(String token) {
        Claims claims = Jwts
                .parser()
                .verifyWith(getSignInKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
        String username = (String) claims.get(JwtClaims.USERNAME);
        String role = (String) claims.get(JwtClaims.ROLE);
        String tokenType = claims.get("tokenType").toString();
        Long id = Long.valueOf(claims.get("id").toString());
        return new JwtDTO(id, username, role, tokenType);
    }

    private static SecretKey getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
