package net.cdsunrise.wm.zuul.security.config;

import net.cdsunrise.wm.zuul.client.UserClient;
import net.cdsunrise.wm.zuul.model.User;
import net.cdsunrise.wm.zuul.security.UserDetails;
import net.cdsunrise.wm.zuul.security.access.MyAccessDecisionManager;
import net.cdsunrise.wm.zuul.security.filter.JwtAuthenticationFilter;
import net.cdsunrise.wm.zuul.security.intercept.MyFilterInvocationSecurityMetadataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

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
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable().authorizeRequests()
                .anyRequest().authenticated()
                .withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {
                    @Override
                    public <O extends FilterSecurityInterceptor> O postProcess(O fis) {
                        fis.setSecurityMetadataSource(myFilterInvocationSecurityMetadataSource());
                        fis.setAccessDecisionManager(myAccessDecisionManager());
                        return fis;
                    }
                })
                .and()
                .logout().permitAll()
                .and()
                .addFilterBefore(new JwtAuthenticationFilter(userDetailsService(),
                                jwtHeader,
                                jwtTokenHead,
                                jwtSecret),
                        UsernamePasswordAuthenticationFilter.class);
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
    public MyAccessDecisionManager myAccessDecisionManager() {
        return new MyAccessDecisionManager();
    }

    @Bean
    public MyFilterInvocationSecurityMetadataSource myFilterInvocationSecurityMetadataSource() {
        return new MyFilterInvocationSecurityMetadataSource();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return (username) -> {
            User user = userClient.findByUsername(username);
            Set<GrantedAuthority> authorities = new HashSet<>(0);
            if (user.getRole() != null) {
                authorities.add(() -> user.getRole().getCode());
            }
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
