package ch.uzh.ifi.hase.soprafs21.security;

import ch.uzh.ifi.hase.soprafs21.entity.User;
import ch.uzh.ifi.hase.soprafs21.rest.dto.OnlineStatusDto;
import ch.uzh.ifi.hase.soprafs21.rest.dto.UserOverviewDto;
import ch.uzh.ifi.hase.soprafs21.security.config.SecurityConstants;
import ch.uzh.ifi.hase.soprafs21.service.JwtTokenUtil;
import ch.uzh.ifi.hase.soprafs21.service.UserService;
import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.env.Environment;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.annotation.DirtiesContext;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
class JwtTokenAuthenticationFilterIntegrationTest {

    JwtTokenAuthenticationFilter jwtFilter;

    @Mock
    private UserService userServiceMock;

    @Autowired
    private Environment env;

    @Autowired
    private JwtTokenUtil jwtTokenUtilWired;

    @Mock
    HttpServletRequest  mockedRequest;
    @Mock
    HttpServletResponse mockedResponse;
    @Mock
    FilterChain mockedFilterChain;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        jwtFilter = new JwtTokenAuthenticationFilter(jwtTokenUtilWired, userServiceMock, env);
    }


    @Test
    void testDoFilterInternalIntegration() throws ServletException, IOException {
        String email = "mock@gmail.com";
        String token = this.jwtTokenUtilWired.generateToken(email);

        when(mockedRequest.getHeader(SecurityConstants.HEADER_STRING)).thenReturn("Bearer "+token);
        User mockedUser = User.builder().id(666L).email("email1").name("name1").profilePictureURL("url1").build();
        when(userServiceMock.getUserByEmail("email1")).thenReturn(Optional.of(mockedUser));
        this.jwtFilter.doFilterInternal(mockedRequest, mockedResponse, mockedFilterChain);
        assertEquals(email, this.jwtTokenUtilWired.getClaimsFromJWT(token, SecurityConstants.SECRET).getSubject());

    }
}