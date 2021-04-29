package ch.uzh.ifi.hase.soprafs21.service;

import ch.uzh.ifi.hase.soprafs21.security.config.SecurityConstants;
import io.jsonwebtoken.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;

@Component
public class JwtTokenUtil {


    public JwtTokenUtil() {
    }

    public String generateToken(String emailId) {

        Long now = System.currentTimeMillis();
        return Jwts.builder()
                .setSubject(emailId)
                .setIssuedAt(new Date(now))
                .setExpiration(new Date(now + SecurityConstants.EXPIRATION_TIME))  // in milliseconds
                .signWith(SignatureAlgorithm.HS512, SecurityConstants.SECRET)
                .compact();
    }

    public String generateRefreshToken(String emailId) {

        Long now = System.currentTimeMillis();
        return Jwts.builder()
                .setSubject(emailId)
                .setIssuedAt(new Date(now))
                .setExpiration(new Date(now + SecurityConstants.REFRESH_EXPIRATION_TIME))  // in milliseconds
                .signWith(SignatureAlgorithm.HS512, SecurityConstants.REFRESH_SECRET)
                .compact();
    }

    public Claims getClaimsFromJWT(String token, String secret) {
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
    }

    public Date getExpirationTimeForAccessToken(String token){
        Claims claims = getClaimsFromJWT(token, SecurityConstants.SECRET);
        return claims.getExpiration();
    }

    public boolean validateToken(String authToken, String secret) {
        try {
            Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(authToken);

            return true;
        } catch (SignatureException ex) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid JWT signature");
        } catch (MalformedJwtException ex) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"JWT claims string is empty.");
        }
    }
}
