package com.github.deetree;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

/**
 * @author Mariusz Bal
 */
@Service
class JwtService {

    private final String packageName = getClass().getPackageName();
    @Value("${secret}")
    private String secretKey;

    String generateToken(String subject, Map<String, Object> extraClaims) {
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(subject)
                .setIssuer(packageName)
                .setIssuedAt(Date.from(Instant.now()))
                .setExpiration(Date.from(Instant.now().plus(1, ChronoUnit.HOURS)))
                .signWith(SignatureAlgorithm.HS512, secretKey.getBytes())
                .compact();
    }

    boolean isValid(String jwt, User user) {
        return isNonExpired(jwt)
                && extractSubject(jwt).equals(user.email())
                && extractIssuer(jwt).equals(packageName);
    }

    private boolean isNonExpired(String jwt) {
        return extractExpiration(jwt).after(Date.from(Instant.now()));
    }

    private String extractSubject(String jwt) {
        return extractClaim(jwt, Claims::getSubject);
    }

    private String extractIssuer(String jwt) {
        return extractClaim(jwt, Claims::getIssuer);
    }

    private Date extractExpiration(String jwt) {
        return extractClaim(jwt, Claims::getExpiration);
    }

    private <T> T extractClaim(String jwt, Function<Claims, T> claimExtractor) {
        return claimExtractor.apply(extractClaims(jwt));
    }

    private Claims extractClaims(String jwt) {
        return Jwts
                .parser()
                .setSigningKey(secretKey.getBytes())
                .parseClaimsJws(jwt)
                .getBody();
    }
}
