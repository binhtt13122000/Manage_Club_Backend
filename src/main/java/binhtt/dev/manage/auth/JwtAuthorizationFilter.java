package binhtt.dev.manage.auth;

import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {
    private static final Logger log = LoggerFactory.getLogger(JwtAuthorizationFilter.class);

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    //chặn những thằng jwt ko hợp lệ
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        UsernamePasswordAuthenticationToken authentication = this.getAuthentication(request);
        if(authentication == null){
            chain.doFilter(request, response);
            return;
        }
        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(request, response);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        String token = "";
        Cookie[] requestCookie = request.getCookies();
        if (requestCookie != null) {
            for (Cookie cookie : requestCookie) {
                if (!cookie.isHttpOnly()) {
                    if (cookie.getName().equals(SecurityConstant.TOKEN_HEADER)) {
                        token = cookie.getValue();
                        break;
                    }
                }
            }
        }

        if (!token.isEmpty()) {
            try {
                String signinKey = SecurityConstant.JWT_SECRET;
                Jws<Claims> parseToken = Jwts.parser().setSigningKey(signinKey.getBytes())
                        .parseClaimsJws(token);
                String username = parseToken.getBody().getSubject();
                List<SimpleGrantedAuthority> authorities = ((List<?>) parseToken.getBody().get("role")).stream()
                        .map(authority -> new SimpleGrantedAuthority("ROLE_" + (String) authority)).collect(Collectors.toList());
                if (!username.isEmpty()) {
                    //Authentication
                    return new UsernamePasswordAuthenticationToken(username, null, authorities);
                }
            } catch (ExpiredJwtException e) {
                log.warn("Request to parse expired JWT : {} failed : {}", token, e.getMessage());
            } catch (UnsupportedJwtException e) {
                log.warn("Request to parse unsupported JWT : {} failed : {}", token, e.getMessage());
            } catch (MalformedJwtException e) {
                log.warn("Request to parse invalid JWT : {} failed : {}", token, e.getMessage());
            } catch (SignatureException e) {
                log.warn("Request to parse JWT with invalid signature : {} failed : {}", token, e.getMessage());
            } catch (IllegalArgumentException e) {
                log.warn("Request to parse empty or null JWT : {} failed : {}", token, e.getMessage());
            }
        }
        return null;
    }
}
