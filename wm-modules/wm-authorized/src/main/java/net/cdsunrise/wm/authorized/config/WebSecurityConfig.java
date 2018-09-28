package net.cdsunrise.wm.authorized.config;

import net.cdsunrise.wm.authorized.client.UserClient;
import net.cdsunrise.wm.authorized.filter.JwtLoginFilter;
import net.cdsunrise.wm.authorized.model.User;
import net.cdsunrise.wm.authorized.model.UserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.HashSet;
import java.util.Set;

/**
 * @author lijun
 * @date 2018-04-16.
 * @descritpion
 */
@EnableWebSecurity
@Configuration
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Value("${jwt.header}")
    private String jwtHeader;
    @Value("${jwt.tokenHead}")
    private String jwtTokenHead;
    @Value("${jwt.secret}")
    private String jwtSecret;
    @Value("${jwt.expiration}")
    private Integer jwtExpiration;
    @Autowired
    private UserClient userClient;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable().authorizeRequests()
                .anyRequest().authenticated()
                .and()
                .logout().permitAll()
                .and()
                .addFilter(new JwtLoginFilter(authenticationManager(),
                        jwtHeader,
                        jwtTokenHead,
                        jwtSecret,
                        jwtExpiration));
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService()).passwordEncoder(bCryptPasswordEncoder());
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return (username) -> {
            User user = userClient.findByUsername(username);
            Set<GrantedAuthority> authorities = new HashSet<>(0);
            UserDetails userDetails =
                    new UserDetails(
                            user.getId(),
                            user.getUsername(),
                            user.getPassword(),
                            authorities);
            return userDetails;
        };
    }

}
