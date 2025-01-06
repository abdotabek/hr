package org.example.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
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
    public static final int accessTokenLiveTime = 3600 * 24;
    private static final int refreshTokenLiveTime = 3600 * 24 * 15;
    private static final String secretKey = "veryLongSecretlasharamazgillattayevaxmojonjinnijonsurbetbekkiydirhonuxlatdibekloxovdangasabekochkozjonduxovmashaynikmaydagapchishularnioqiganbolsangizgapyoqaniqsizmazgi";


    public static String generateToken(String username, String role, long tokenLiveTime, String tokenType) {
        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("tokenType", tokenType); // Добавляю тип токена чтобы refresh token не обращал на прямую на l end point
        extraClaims.put(JwtClaims.USERNAME, username);
        extraClaims.put(JwtClaims.ROLE, role);

        return Jwts
                .builder()
                .claims(extraClaims)
                .subject(username)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + tokenLiveTime * 1000))
                .signWith(getSignInKey())
                .compact();
    }

    public static String encode(String username, String role) {
        return generateToken(username, role, accessTokenLiveTime, "access");
    }

    public static String generationRefreshToken(String username, String role) {
        return generateToken(username, role, refreshTokenLiveTime, "refresh");
    }

  /*  public static boolean isValid(String token) {
        return Jwts
                .parser()
                .verifyWith(getSignInKey())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getExpiration().after(new Date());
    }
*/
  public static boolean isValid(String token) {
      try {
          // Parse the token
          Jws<Claims> claims = Jwts.parser()
                  .verifyWith(getSignInKey())
                  .build()
                  .parseSignedClaims(token);

          Date expiration = claims.getPayload().getExpiration();
          return expiration != null && expiration.after(new Date());
      } catch (JwtException e) {
          return false;
      }
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
        return new JwtDTO(username, role, tokenType);
    }

    private static SecretKey getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
