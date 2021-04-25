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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

import static org.mockito.Mockito.*;

class JwtTokenAuthenticationFilterTest {


    JwtTokenAuthenticationFilter jwtFilter;

    @Mock
    private UserService userServiceMock;

    @Mock
    private JwtTokenUtil jwtTokenUtil;

    @Mock
    private Authentication authenticationMock;

    @Mock
    private SecurityContext securityContextMock;


    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        UserOverviewDto userOverviewDto = new UserOverviewDto();
        userOverviewDto.setEmail("mark@twen.de");
        userOverviewDto.setId(1L);
        userOverviewDto.setName("mark");
        userOverviewDto.setStatus(OnlineStatusDto.ONLINE);
        Mockito.when(authenticationMock.getPrincipal()).thenReturn(userOverviewDto);
        jwtFilter=new JwtTokenAuthenticationFilter(jwtTokenUtil, userServiceMock);
        Mockito.when(securityContextMock.getAuthentication()).thenReturn(authenticationMock);
        SecurityContextHolder.setContext(securityContextMock);
    }

    @Test
    void testDoFilterInternal() throws ServletException, IOException {
        HttpServletRequest  mockedRequest = Mockito.mock(HttpServletRequest.class);
        HttpServletResponse mockedResponse = Mockito.mock(HttpServletResponse.class);
        FilterChain mockedFilterChain = Mockito.mock(FilterChain.class);
        Claims mockedClaims = Mockito.mock(Claims.class);

        User mockedUser = User.builder().id(1L).email("email1").name("name1").profilePictureURL("url1").build();

        when(mockedRequest.getHeader(SecurityConstants.HEADER_STRING)).thenReturn("Bearer Test123");
        when(jwtTokenUtil.validateToken("Test123",SecurityConstants.SECRET)).thenReturn(Boolean.TRUE);
        when(jwtTokenUtil.getClaimsFromJWT("Test123",SecurityConstants.SECRET)).thenReturn(mockedClaims);
        when(mockedClaims.getSubject()).thenReturn("email1");
        when(userServiceMock.getUserByEmail("email1")).thenReturn(Optional.of(mockedUser));

        jwtFilter.doFilterInternal(mockedRequest,mockedResponse,mockedFilterChain);
        verify(jwtTokenUtil,times(1)).validateToken("Test123",SecurityConstants.SECRET);
        verify(jwtTokenUtil,times(1)).getClaimsFromJWT("Test123",SecurityConstants.SECRET);
        verify(userServiceMock,times(1)).getUserByEmail("email1");

    }

    @Test
    void testDoFilterInternalNoHeader() throws ServletException, IOException {
        HttpServletRequest  mockedRequest = Mockito.mock(HttpServletRequest.class);
        HttpServletResponse mockedResponse = Mockito.mock(HttpServletResponse.class);
        FilterChain mockedFilterChain = Mockito.mock(FilterChain.class);

        when(mockedRequest.getHeader(SecurityConstants.HEADER_STRING)).thenReturn(null);

        jwtFilter.doFilterInternal(mockedRequest,mockedResponse,mockedFilterChain);
        verify(jwtTokenUtil,times(0)).validateToken("Test123",SecurityConstants.SECRET);
        verify(jwtTokenUtil,times(0)).getClaimsFromJWT("Test123",SecurityConstants.SECRET);
        verify(userServiceMock,times(0)).getUserByEmail("email1");

    }

    @Test
    void testDoFilterInternalWrongHeader() throws ServletException, IOException {
        HttpServletRequest  mockedRequest = Mockito.mock(HttpServletRequest.class);
        HttpServletResponse mockedResponse = Mockito.mock(HttpServletResponse.class);
        FilterChain mockedFilterChain = Mockito.mock(FilterChain.class);

        when(mockedRequest.getHeader(SecurityConstants.HEADER_STRING)).thenReturn("Null");

        jwtFilter.doFilterInternal(mockedRequest,mockedResponse,mockedFilterChain);
        verify(jwtTokenUtil,times(0)).validateToken("Test123",SecurityConstants.SECRET);
        verify(jwtTokenUtil,times(0)).getClaimsFromJWT("Test123",SecurityConstants.SECRET);
        verify(userServiceMock,times(0)).getUserByEmail("email1");

    }

    @Test
    void testDoFilterInternalInvalidToken() throws ServletException, IOException {
        HttpServletRequest  mockedRequest = Mockito.mock(HttpServletRequest.class);
        HttpServletResponse mockedResponse = Mockito.mock(HttpServletResponse.class);
        FilterChain mockedFilterChain = Mockito.mock(FilterChain.class);

        when(mockedRequest.getHeader(SecurityConstants.HEADER_STRING)).thenReturn("Bearer Test123");
        when(jwtTokenUtil.validateToken("Test123",SecurityConstants.SECRET)).thenReturn(Boolean.FALSE);

        jwtFilter.doFilterInternal(mockedRequest,mockedResponse,mockedFilterChain);
        verify(jwtTokenUtil,times(1)).validateToken("Test123",SecurityConstants.SECRET);
        verify(jwtTokenUtil,times(0)).getClaimsFromJWT("Test123",SecurityConstants.SECRET);
        verify(userServiceMock,times(0)).getUserByEmail("email1");

    }
}