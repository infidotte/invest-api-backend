package api.invest.service;

import api.invest.entity.User;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Slf4j
@Component
public class JwtProvider {
    private final SecretKey jwtAccessSecret;

    private final SecretKey jwtRefreshSecret;

    private final int accessExpirationMinutes;

    private final int refreshExpirationDays;

    public JwtProvider(
            @Value("${jwt.secret.access}") String jwtAccessSecret,
            @Value("${jwt.secret.refresh}") String jwtRefreshSecret,
            @Value("${jwt.accessExpirationMinutes}") int accessExpirationMinutes,
            @Value("${jwt.refreshExpirationDays}") int refreshExpirationDays
    ) {
        this.jwtAccessSecret = Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtAccessSecret));
        this.jwtRefreshSecret = Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtRefreshSecret));
        this.accessExpirationMinutes = accessExpirationMinutes;
        this.refreshExpirationDays = refreshExpirationDays;
    }

    public String generateAccessToken(User user) {
        final Date accessExpiration =
                Date.from(
                        LocalDateTime.now()
                                .plusMinutes(accessExpirationMinutes)
                                .atZone(ZoneId.systemDefault())
                                .toInstant());
        return Jwts.builder()
                .subject(user.getUsername())
                .expiration(accessExpiration)
                .signWith(jwtAccessSecret)
                .claim("email", user.getEmail())
                .claim("role", user.getRole())
                .compact();
    }

    public String generateRefreshToken(User user) {
        final Date refreshExpiration = Date.from(
                LocalDateTime.now()
                        .plusDays(refreshExpirationDays)
                        .atZone(ZoneId.systemDefault())
                        .toInstant());
        return Jwts.builder()
                .subject(user.getUsername())
                .expiration(refreshExpiration)
                .signWith(jwtRefreshSecret)
                .compact();
    }

    public boolean isAccessTokenValid(String accessToken) {
        return isTokenValid(accessToken, jwtAccessSecret);
    }

    public boolean isRefreshTokenValid(String refreshToken) {
        return isTokenValid(refreshToken, jwtRefreshSecret);
    }

    public Claims getRefreshClaims(String token) {
        return getClaims(token, jwtRefreshSecret);
    }

    private Claims getClaims(String token, SecretKey secret) {
        return Jwts.parser()
                .verifyWith(secret)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private boolean isTokenValid(String token, SecretKey key) {
        try {
            Jwts.parser().verifyWith(key).build().parseSignedClaims(token);
            return true;
        } catch (ExpiredJwtException expEx) {
            log.error("Token expired", expEx);
        } catch (UnsupportedJwtException unsEx) {
            log.error("Unsupported jwt", unsEx);
        } catch (MalformedJwtException mjEx) {
            log.error("Malformed jwt", mjEx);
        } catch (SignatureException signatureException) {
            log.error("Invalid signature", signatureException);
        } catch (Exception e) {
            log.error("invalid token", e);
        }
        return false;
    }
}
