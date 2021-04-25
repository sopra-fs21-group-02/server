package ch.uzh.ifi.hase.soprafs21.security;

import ch.uzh.ifi.hase.soprafs21.rest.dto.OnlineStatusDto;
import ch.uzh.ifi.hase.soprafs21.rest.dto.UserOverviewDto;
import ch.uzh.ifi.hase.soprafs21.service.JwtTokenUtil;
import ch.uzh.ifi.hase.soprafs21.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
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
import static org.junit.jupiter.api.Assertions.*;
import java.io.IOException;

import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.*;

class JwtTokenAuthenticationFilterTest {

    @Mock
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
    void doFilterInternal() throws ServletException, IOException {
        HttpServletRequest  mockedRequest = Mockito.mock(HttpServletRequest.class);
        HttpServletResponse mockedResponse = Mockito.mock(HttpServletResponse.class);
        FilterChain mockedFilterChain = Mockito.mock(FilterChain.class);
        jwtFilter.doFilterInternal(mockedRequest,mockedResponse,mockedFilterChain);

        doNothing().when(jwtFilter).doFilterInternal(isA(HttpServletRequest.class), isA(HttpServletResponse.class), isA(FilterChain.class));
        jwtFilter.doFilterInternal(mockedRequest, mockedResponse, mockedFilterChain);
        verify(jwtFilter, times(1)).doFilterInternal(mockedRequest, mockedResponse, mockedFilterChain);
    }
}