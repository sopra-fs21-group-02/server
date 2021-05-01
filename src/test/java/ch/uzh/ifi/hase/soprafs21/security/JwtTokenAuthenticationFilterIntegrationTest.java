package ch.uzh.ifi.hase.soprafs21.security;

import ch.uzh.ifi.hase.soprafs21.entity.User;
import ch.uzh.ifi.hase.soprafs21.rest.dto.OnlineStatusDto;
import ch.uzh.ifi.hase.soprafs21.rest.dto.UserOverviewDto;
import ch.uzh.ifi.hase.soprafs21.security.config.SecurityConstants;
import ch.uzh.ifi.hase.soprafs21.service.JwtTokenUtil;
import ch.uzh.ifi.hase.soprafs21.service.UserService;
import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

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


    @Autowired
    JwtTokenAuthenticationFilter jwtFilter;

    @MockBean
    private UserService userServiceMock;

    @Mock
    private Authentication authenticationMock;

    @Mock
    private SecurityContext securityContextMock;

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
        UserOverviewDto userOverviewDto = new UserOverviewDto();
        userOverviewDto.setEmail("mark@twen.de");
        userOverviewDto.setId(1L);
        userOverviewDto.setName("mark");
        userOverviewDto.setStatus(OnlineStatusDto.ONLINE);
        Mockito.when(authenticationMock.getPrincipal()).thenReturn(userOverviewDto);
        Mockito.when(securityContextMock.getAuthentication()).thenReturn(authenticationMock);
        SecurityContextHolder.setContext(securityContextMock);
    }


    @Test
    void testDoFilterInternalIntegration() throws ServletException, IOException {
        String email = "mock@gmail.com";
        String token = this.jwtTokenUtilWired.generateToken(email);

        when(mockedRequest.getHeader(SecurityConstants.HEADER_STRING)).thenReturn("Bearer "+token);
        User mockedUser = User.builder().id(1L).email("email1").name("name1").profilePictureURL("url1").build();
        when(userServiceMock.getUserByEmail("email1")).thenReturn(Optional.of(mockedUser));
        this.jwtFilter.doFilterInternal(mockedRequest, mockedResponse, mockedFilterChain);
        assertEquals(email, this.jwtTokenUtilWired.getClaimsFromJWT(token, SecurityConstants.SECRET).getSubject());

    }
}