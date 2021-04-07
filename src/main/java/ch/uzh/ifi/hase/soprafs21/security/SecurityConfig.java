package ch.uzh.ifi.hase.soprafs21.security;

import ch.uzh.ifi.hase.soprafs21.service.JwtTokenUtil;
import ch.uzh.ifi.hase.soprafs21.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletResponse;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private UserService userService;


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors()
                .and().csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().exceptionHandling()
                .authenticationEntryPoint((req, rsp, e) -> rsp.sendError(HttpServletResponse.SC_UNAUTHORIZED))
                .and()
                .addFilterBefore(new JwtTokenAuthenticationFilter(jwtTokenUtil, userService), UsernamePasswordAuthenticationFilter.class)
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, "/users/*","/message","/parks/*","/paths/*").permitAll()
                .antMatchers(HttpMethod.GET, "/users/*","/parks/*","/paths/*").permitAll()
                .antMatchers(HttpMethod.PUT, "/users/*").permitAll()
                .antMatchers(HttpMethod.DELETE, "/users/*","/parks/*","/paths/*").permitAll()
                .anyRequest().authenticated();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
                .antMatchers("/v1/users/login");
    }
}
