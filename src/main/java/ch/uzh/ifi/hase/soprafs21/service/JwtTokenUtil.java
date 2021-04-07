package ch.uzh.ifi.hase.soprafs21.service;

import ch.uzh.ifi.hase.soprafs21.security.config.SecurityConstants;
import io.jsonwebtoken.*;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
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

    public Claims getClaimsFromJWT(String token) {
        return Jwts.parser()
                .setSigningKey(SecurityConstants.SECRET)
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean validateToken(String authToken) {
        try {
            Jwts.parser()
                    .setSigningKey(SecurityConstants.SECRET)
                    .parseClaimsJws(authToken);

            return true;
        } catch (SignatureException ex) {
            System.out.println("Invalid JWT signature");
        } catch (MalformedJwtException ex) {
            System.out.println("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            System.out.println("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            System.out.println("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            System.out.println("JWT claims string is empty.");
        }
        return false;
    }
}
