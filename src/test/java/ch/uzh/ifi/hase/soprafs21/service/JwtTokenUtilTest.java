package ch.uzh.ifi.hase.soprafs21.service;

import ch.uzh.ifi.hase.soprafs21.security.config.SecurityConstants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class JwtTokenUtilTest {


    JwtTokenUtil jwtTokenUtil;

    @BeforeEach
    public void setup() {
        jwtTokenUtil = new JwtTokenUtil();
    }

    @Test
    void generateToken() {
        String email = "mock@gmail.com";
        String token = this.jwtTokenUtil.generateToken(email);
        assertEquals(Boolean.TRUE, this.jwtTokenUtil.validateToken(token, SecurityConstants.SECRET));
    }

    @Test
    void generateRefreshToken() {
        String email = "mock@gmail.com";
        String token = this.jwtTokenUtil.generateRefreshToken(email);
        assertEquals(Boolean.TRUE, this.jwtTokenUtil.validateToken(token, SecurityConstants.REFRESH_SECRET));
    }

    @Test
    void validateTokenForUnauthorized() {
        String token = "DUMMYTOKEN";
        Exception exception = assertThrows(ResponseStatusException.class, () -> {
            this.jwtTokenUtil.validateToken(token, SecurityConstants.SECRET);
        });
        assertEquals("401 UNAUTHORIZED \"Invalid JWT token\"",exception.getMessage());
    }
}