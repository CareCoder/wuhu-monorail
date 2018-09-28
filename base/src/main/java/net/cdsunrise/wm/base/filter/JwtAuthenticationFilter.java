package net.cdsunrise.wm.base.filter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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

    private String jwtHeader;
    private String jwtTokenHead;
    private String jwtSecret;

    public JwtAuthenticationFilter(String jwtHeader, String jwtTokenHead, String jwtSecret) {
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
        Claims body = Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(authToken.replace(jwtTokenHead, ""))
                .getBody();
        String username = body.getSubject();
        String userId = body.getId();
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(username, null);
            authentication.setDetails(userId);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        chain.doFilter(request, response);
    }
}
