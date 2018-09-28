package net.cdsunrise.wm.zuul.security.filter;

import io.jsonwebtoken.Jwts;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

/**
 * token的校验
 * 该类继承自BasicAuthenticationFilter，在doFilterInternal方法中，
 * 从http头的Authorization 项读取token数据，然后用Jwts包提供的方法校验token的合法性。
 * 如果校验通过，就认为这是一个取得授权的合法请求
 *
 * @author lijun
 * @date 2018-04-16.
 * @descritpion BasicAuthenticationFilter  OncePerRequestFilter
 */
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private UserDetailsService userDetailsService;
    private String jwtHeader;
    private String jwtTokenHead;
    private String jwtSecret;

    public JwtAuthenticationFilter(UserDetailsService userDetailsService, String jwtHeader, String jwtTokenHead, String jwtSecret) {
        this.userDetailsService = userDetailsService;
        this.jwtHeader = jwtHeader;
        this.jwtTokenHead = jwtTokenHead;
        this.jwtSecret = jwtSecret;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String authToken = request.getHeader(jwtHeader);
        if (authToken == null || !authToken.startsWith(jwtTokenHead)) {
            chain.doFilter(request, response);
            return;
        }
        String username = Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(authToken.replace(jwtTokenHead, ""))
                .getBody()
                .getSubject();
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails.getUsername(), userDetails.getPassword(), userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        chain.doFilter(request, response);
    }
}
