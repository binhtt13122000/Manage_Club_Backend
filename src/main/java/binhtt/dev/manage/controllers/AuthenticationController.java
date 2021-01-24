package binhtt.dev.manage.controllers;

import binhtt.dev.manage.auth.JwtUtils;
import binhtt.dev.manage.auth.MyUserDetailService;
import binhtt.dev.manage.auth.SecurityConstant;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;


@Data
class AuthenticationRequest {
    private String username;
    private String password;
}

@RestController
@RequestMapping("/v1/api")
public class AuthenticationController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private MyUserDetailService userDetailService;

    @PostMapping(path = "/login")
    public ResponseEntity login(@RequestBody AuthenticationRequest request) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
            UserDetails user = userDetailService.loadUserByUsername(request.getUsername());
            String token = jwtUtils.generateToken(user);
            HttpHeaders headers = new HttpHeaders();
            String cookieToken = SecurityConstant.TOKEN_HEADER + "=" + token;
            headers.add("Set-Cookie",
                    cookieToken + "; HttpOnly; SameSite=None; Max-Age=864000");
            return new ResponseEntity("Login Successfully!",headers, HttpStatus.OK);
        } catch (BadCredentialsException e) {
            return new ResponseEntity("Username or Password is incorrect", HttpStatus.UNAUTHORIZED);
        }

    }

    @PostMapping("/logout")
    public String logout() {
        return "Logout Successfully";
    }

}
