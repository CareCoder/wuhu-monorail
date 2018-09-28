package net.cdsunrise.wm.base.filter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author lijun
 * @date 2018-04-20.
 */
public class JwtFilter implements HandlerInterceptor {
    private String jwtHeader;
    private String jwtTokenHead;
    private String jwtSecret;
    private Integer jwtExpiration;

    public JwtFilter(String jwtHeader, String jwtTokenHead, String jwtSecret, Integer jwtExpiration) {
        this.jwtHeader = jwtHeader;
        this.jwtTokenHead = jwtTokenHead;
        this.jwtSecret = jwtSecret;
        this.jwtExpiration = jwtExpiration;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
        String authToken = request.getHeader(jwtHeader);
        if (authToken == null || !authToken.startsWith(jwtTokenHead)) {
            return true;
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
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
