package com.openclassrooms.mddapi.security.jwt;

import java.util.Date;

import javax.crypto.SecretKey;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.openclassrooms.mddapi.security.services.UserDetailsImpl;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;

/* Service to handle JWT authentication */
@Component
public class JwtUtils {
  private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

  /**
   * Secret key used to encode and decode JWT tokens
   */
  private final SecretKey jwtSecret = Keys.secretKeyFor(SignatureAlgorithm.HS512);

  /**
   * Delay for token expiration (ms)
   */
  @Value("${oc.app.jwtExpirationMs}")
  private int jwtExpirationMs;

  /**
   * Generate a new token for the authenticated user who's been registered ou just
   * logged
   * 
   * @param authentication Represent the token for the authenticated user
   * @return a jwtToken for the authenticated user
   */
  public String generateJwtToken(final Authentication authentication) {

    final UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();

    return Jwts.builder()
        .setSubject((userPrincipal.getUsername()))
        .setIssuedAt(new Date())
        .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
        .signWith(jwtSecret, SignatureAlgorithm.HS512)
        .compact();
  }

  /**
   * Extract the username from the JwtToken
   * 
   * @param token a jwtToken of the authenticated user
   * @return the userName from JwtToken
   */
  public String getUserNameFromJwtToken(final String token) {
    return Jwts.parserBuilder()
        .setSigningKey(jwtSecret)
        .build()
        .parseClaimsJws(token)
        .getBody()
        .getSubject();
  }

  /**
   * Verify is the token is valid
   * 
   * @param authToken a jwtToken of the authenticated user
   * @return the validation status, true or false
   */
  public boolean validateJwtToken(final String authToken) {
    try {
      Jwts.parserBuilder().setSigningKey(jwtSecret).build().parseClaimsJws(authToken);
      return true;
    } catch (MalformedJwtException e) {
      logger.error("Invalid JWT token: {}", e.getMessage());
    } catch (ExpiredJwtException e) {
      logger.error("JWT token is expired: {}", e.getMessage());
    } catch (UnsupportedJwtException e) {
      logger.error("JWT token is unsupported: {}", e.getMessage());
    } catch (IllegalArgumentException e) {
      logger.error("JWT claims string is empty: {}", e.getMessage());
    }

    return false;
  }
}
