package net.cdsunrise.wm.authorized.filter;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import net.cdsunrise.wm.authorized.model.UserDetails;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.WildcardType;
import java.util.Date;

/**
 * @author lijun
 * @date 2018-04-16.
 * @descritpion
 */
public class JwtLoginFilter extends UsernamePasswordAuthenticationFilter {

    private AuthenticationManager authenticationManager;

    private String jwtHeader;
    private String jwtTokenHead;
    private String jwtSecret;
    private Integer jwtExpiration;

    public JwtLoginFilter(AuthenticationManager authenticationManager,
                          String jwtHeader,
                          String jwtTokenHead,
                          String jwtSecret,
                          Integer jwtExpiration) {
        this.authenticationManager = authenticationManager;
        this.jwtHeader = jwtHeader;
        this.jwtTokenHead = jwtTokenHead;
        this.jwtSecret = jwtSecret;
        this.jwtExpiration = jwtExpiration;
    }

    /**
     * 接收并解析用户凭证
     *
     * @param request
     * @param response
     * @return
     * @throws AuthenticationException
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
    }

    /**
     * 登录失败后该方法会被调用
     *
     * @param request
     * @param response
     * @param failed
     * @throws IOException
     * @throws ServletException
     */
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
//        super.unsuccessfulAuthentication(request, response, failed);
        response.setStatus(401);
        response.setHeader("Content-Type", "text/html;charset=UTF-8");
        PrintWriter writer = response.getWriter();
        writer.write("{\"code\": 401, \"msg\": \"授权失败！\"}");

        writer.flush();
        writer.close();
    }

    /**
     * 用户成功登录后，这个方法会被调用，我们在这个方法里生成token
     *
     * @param request
     * @param response
     * @param chain
     * @param auth
     * @throws IOException
     * @throws ServletException
     */
    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication auth) throws IOException {
        String token = Jwts.builder()
                .setSubject(((UserDetails) auth.getPrincipal()).getUsername())
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpiration))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .setId(((UserDetails) auth.getPrincipal()).getId().toString())
                .compact();
        PrintWriter writer = response.getWriter();
        response.addHeader(jwtHeader, jwtTokenHead + token);
        response.setHeader("Content-Type", "text/html;charset=UTF-8");
        writer.write(jwtTokenHead + token);
        writer.flush();
        writer.close();
    }

}
