package com.caito.booksnapi.configs.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.function.Function;

/**
 * Service class for handling JWT operations such as token generation, validation, and extraction of claims.
 * It uses a secret key to sign the tokens and provides methods to generate and validate JWTs.
 *
 * @author caito
 *
 */
@Service
public class JwtService {
    private long jwtExpiration = 1000 * 60 * 24; // 24 hours in milliseconds
    private String secretKey = "Hw9z1Yk8Nmq1IzlwcCg8j6yHzw6RKjzZUi9r7Ww555o0PP";

    /**
     * Generates a JWT token for the given user details with optional extra claims.
     *
     * @param userDetails  User details for which the token is generated
     * @return             Generated JWT token as a String
     */
    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }

    /**
     * Generates a JWT token with extra claims for the given user details.
     *
     * @param claims       Extra claims to be included in the token
     * @param userDetails  User details for which the token is generated
     * @return             Generated JWT token as a String
     */
    public String generateToken(HashMap<String, Object> claims, UserDetails userDetails) {
        return buildToken(claims, userDetails, jwtExpiration);
    }

    /**
     * Generates a JWT token with extra claims and a specified expiration time for the given user details.
     *
     * @param extraClaims  Extra claims to be included in the token
     * @param userDetails  User details for which the token is generated
     * @param jwtExpiration Expiration time for the token in milliseconds
     * @return             Generated JWT token as a String
     */
    private String buildToken(HashMap<String, Object> extraClaims, UserDetails userDetails, long jwtExpiration) {
        var authorities = userDetails.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .toList();
        return Jwts.builder()
                .claims(extraClaims)
                .subject(userDetails.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + jwtExpiration))
                .claim("authorities", authorities)
                .signWith(getSigningKey())
                .compact();
    }

    /**
     * Validates the JWT token against the provided user details.
     *
     * @param token        JWT token to be validated
     * @param userDetails  User details for which the token is validated
     * @return             True if the token is valid, false otherwise
     */
    public boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }


    /**
     * Extracts the username from the JWT token.
     *
     * @param token JWT token from which the username is extracted
     * @return      Username extracted from the token
     */
    public String extractUsername(String token) {
        return extractClaims(token, Claims::getSubject);
    }

    /**
     * Extracts claims from the JWT token using a provided function.
     *
     * @param token           JWT token from which claims are extracted
     * @param claimsResolver  Function to resolve claims from the token
     * @param <T>             Type of the claims to be resolved
     * @return                Resolved claims of type T
     */
    public <T> T extractClaims(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    /**
     * Extracts all claims from the JWT token.
     *
     * @param token JWT token from which all claims are extracted
     * @return      Claims extracted from the token
     */
    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * Checks if the JWT token is expired.
     *
     * @param token JWT token to be checked for expiration
     * @return      True if the token is expired, false otherwise
     */
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    /**
     * Extracts the expiration date from the JWT token.
     *
     * @param token JWT token from which the expiration date is extracted
     * @return      Expiration date of the token
     */
    private Date extractExpiration(String token) {
        return extractClaims(token, Claims::getExpiration);
    }

    /**
     * Retrieves the signing key used for signing the JWT tokens.
     *
     * @return Key used for signing the JWT tokens
     */
    private Key getSigningKey() {
        byte[] keyBytes = Base64.getDecoder().decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
