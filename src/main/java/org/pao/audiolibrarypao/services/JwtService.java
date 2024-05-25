package org.pao.audiolibrarypao.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.*;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import lombok.Setter;
import org.pao.audiolibrarypao.entities.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

/**
 * Service class for managing JSON Web Tokens (JWT).
 */
@Service
public class JwtService {

    @Value("${jwt.secret}")
    @Setter
    private String secretKey;

    @Value("${jwt.expiration}")
    @Setter
    private long jwtExpiration;

    private final ObjectMapper objectMapper;

    /**
     * Constructs a new JwtService with the specified ObjectMapper.
     *
     * @param objectMapper the ObjectMapper used for JSON conversion
     */
    public JwtService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    /**
     * Extracts the username from the given JWT token.
     *
     * @param token the JWT token
     * @return the username extracted from the token
     */
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * Extracts the user details from the given JWT token.
     *
     * @param token the JWT token
     * @return an Optional containing the User object if present, otherwise an empty Optional
     */
    public Optional<User> extractUser(String token) {
        Claims claims = extractAllClaims(token);
        Map<String, Object> userMap = claims.get("user", Map.class);
        return Optional.ofNullable(objectMapper.convertValue(userMap, User.class));
    }

    /**
     * Extracts a specific claim from the given JWT token.
     *
     * @param <T> the type of the claim
     * @param token the JWT token
     * @param claimsResolver a function to resolve the claim from the token
     * @return the claim extracted from the token
     */
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    /**
     * Extracts all claims from the given JWT token.
     *
     * @param token the JWT token
     * @return the claims extracted from the token
     */
    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey.getBytes())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * Checks if the given JWT token is valid for the specified user details.
     *
     * @param token the JWT token
     * @param userDetails the user details to validate the token against
     * @return true if the token is valid, false otherwise
     */
    public boolean isTokenValid(String token, UserDetails userDetails) {
        try {
            final String username = extractUsername(token);
            return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
        } catch (ExpiredJwtException e) {
            return false;
        }
    }

    /**
     * Checks if the given JWT token is expired.
     *
     * @param token the JWT token
     * @return true if the token is expired, false otherwise
     */
    private boolean isTokenExpired(String token) {
        try {
            return extractExpiration(token).before(new Date());
        } catch (ExpiredJwtException e) {
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Extracts the expiration date from the given JWT token.
     *
     * @param token the JWT token
     * @return the expiration date extracted from the token
     */
    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    /**
     * Generates a JWT token for the given user details and user.
     *
     * @param userDetails the user details to include in the token
     * @param user the user to include in the token
     * @return the generated JWT token
     */
    public String generateToken(UserDetails userDetails, User user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("user", user);
        return createToken(claims, userDetails.getUsername());
    }

    /**
     * Generates a JWT token for the given user details.
     *
     * @param userDetails the user details to include in the token
     * @return the generated JWT token
     */
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, userDetails.getUsername());
    }

    /**
     * Creates a JWT token with the specified claims and subject.
     *
     * @param claims the claims to include in the token
     * @param subject the subject of the token
     * @return the generated JWT token
     */
    private String createToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpiration))
                .signWith(SignatureAlgorithm.HS256, secretKey.getBytes())
                .compact();
    }
}
