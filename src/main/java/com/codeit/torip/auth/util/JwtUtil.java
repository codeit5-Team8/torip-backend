package com.codeit.torip.auth.util;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtUtil {

    private final Long expiredAccessTokenMs;
    private final Long expiredRefreshTokenMs;
    private final SecretKey secretKey;

    public JwtUtil(@Value("${jwt.secretKey}") String secretKey,
                   @Value("${jwt.expiredAccessTokenMs}") Long expiredAccessTokenMs,
                   @Value("${jwt.expiredRefreshTokenMs}") Long expiredRefreshTokenMs) {
        this.secretKey = new SecretKeySpec(secretKey.getBytes(StandardCharsets.UTF_8), Jwts.SIG.HS256.key().build().getAlgorithm());
        this.expiredAccessTokenMs = expiredAccessTokenMs;
        this.expiredRefreshTokenMs = expiredRefreshTokenMs;
    }

    public String getLoginId(String token) {
        return getClaim(token, "loginId");
    }

    public Boolean isExpired(String token) {
        try {
            return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().getExpiration().before(new Date());
        } catch (ExpiredJwtException e) {
            return true;
        }
    }

    public String createAccessToken(String loginId) {
        return Jwts.builder()
                .claim("loginId", loginId)
                .claim("type", "access")
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expiredAccessTokenMs))
                .signWith(secretKey)
                .compact();
    }

    public String createRefreshToken(String loginId) {
        return Jwts.builder()
                .claim("loginId", loginId)
                .claim("type", "refresh")
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expiredRefreshTokenMs))
                .signWith(secretKey)
                .compact();
    }

    public String refreshAccessToken(String refreshToken) {
        if (isExpired(refreshToken)) {
            throw new ExpiredJwtException(null, null, "만료된 토큰 값입니다.");
        }

        String tokenType = getClaim(refreshToken, "type");

        if (!"refresh".equals(tokenType)) {
            throw new RuntimeException("토큰값이 다릅니다.");
        }

        String loginId = getLoginId(refreshToken);

        return createAccessToken(loginId);
    }

    private String getClaim(String token, String name) {
        return Jwts.parser().verifyWith(secretKey).build()
                .parseSignedClaims(token)
                .getPayload()
                .get(name, String.class);
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = getLoginId(token);
        return (username.equals(userDetails.getUsername()) && !isExpired(token));
    }

}
