package binhtt.dev.manage.auth;


import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager){
        super();
        this.authenticationManager = authenticationManager;
        setFilterProcessesUrl(SecurityConstant.AUTH_LOGIN_URL);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        Authentication authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
        //contextHolder
        return authenticationManager.authenticate(authenticationToken);
    }


    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        //sinh token
        UserPrincipal user = (UserPrincipal) authResult.getPrincipal();
        List<String> roles = user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
        String signinKey = SecurityConstant.JWT_SECRET;
        String token = Jwts.builder()
                .signWith(Keys.hmacShaKeyFor(signinKey.getBytes()), SignatureAlgorithm.HS512)
                .setHeaderParam("type", SecurityConstant.TOKEN_TYPE)
                .setIssuer(SecurityConstant.TOKEN_ISSUER)
                .setAudience(SecurityConstant.TOKEN_AUDIENCE)
                .setSubject(user.getUsername())
                .setExpiration(new Date(System.currentTimeMillis() + 864000000))
                .claim("role", roles)
                .compact();
        String cookieToken = SecurityConstant.TOKEN_HEADER + "=" + token;
        //tra ve
        response.addHeader("Set-Cookie",
                cookieToken + "; HttpOnly; SameSite=None; Max-Age=864000");
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    }
}
