package ch.uzh.ifi.hase.soprafs21.security;

import ch.uzh.ifi.hase.soprafs21.rest.mapper.UserDTOMapper;
import ch.uzh.ifi.hase.soprafs21.security.config.SecurityConstants;
import ch.uzh.ifi.hase.soprafs21.service.JwtTokenUtil;
import ch.uzh.ifi.hase.soprafs21.service.UserService;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

@Component
public class JwtTokenAuthenticationFilter extends OncePerRequestFilter {

    private JwtTokenUtil jwtTokenUtil;
    private UserService userService;
    private Environment env;

    @Autowired
    public JwtTokenAuthenticationFilter(
            JwtTokenUtil jwtTokenUtil,
            UserService userService,
            Environment env) {

        this.jwtTokenUtil = jwtTokenUtil;
        this.userService = userService;
        this.env = env;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String header = request.getHeader(SecurityConstants.HEADER_STRING);

        if(header == null || !header.startsWith(SecurityConstants.TOKEN_PREFIX)) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        String token = header.replace(SecurityConstants.TOKEN_PREFIX, "");

        // if "dev" profile is activated, use the content of the token as email to fake authentication
        if (Arrays.stream(env.getActiveProfiles()).anyMatch(profile -> "dev".equals(profile))) {
            UsernamePasswordAuthenticationToken auth =
                    getUsernamePasswordAuthenticationToken(request, token);

            SecurityContextHolder.getContext().setAuthentication(auth);
            filterChain.doFilter(request, response);
            return;
        }

        if(jwtTokenUtil.validateToken(token, SecurityConstants.SECRET)) {
            Claims claims = jwtTokenUtil.getClaimsFromJWT(token, SecurityConstants.SECRET);
            String emailId = claims.getSubject();

            UsernamePasswordAuthenticationToken auth =
                    getUsernamePasswordAuthenticationToken(request, emailId);

            SecurityContextHolder.getContext().setAuthentication(auth);
        } else {
            SecurityContextHolder.clearContext();
        }

        filterChain.doFilter(request, response);

    }

    private UsernamePasswordAuthenticationToken getUsernamePasswordAuthenticationToken(HttpServletRequest request, String emailId) {
        return userService.getUserByEmail(emailId)
                .map(userDetails -> {
                    UsernamePasswordAuthenticationToken authentication =
                            new UsernamePasswordAuthenticationToken(
                                    UserDTOMapper.INSTANCE.toOverviewDTO(userDetails), null, null);
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    return authentication;
                })
                .orElse(null);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String path = request.getRequestURI();

        // Exclude the login URL from the filter.
        return "/v1/users/login".equals(path);
    }
}
