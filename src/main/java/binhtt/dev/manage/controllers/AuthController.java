package binhtt.dev.manage.controllers;

import binhtt.dev.manage.entities.UserEntity;
import binhtt.dev.manage.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
public class AuthController {
    @Autowired
    private UserService userService;
    @PostMapping(path = "/login")
    public String login(HttpServletRequest request){
        return "Login successfully";
    }

    @PostMapping("/logout")
    public String logout(){
        return "Logout Successfully";
    }

}
