package binhtt.dev.manage.controllers;

import binhtt.dev.manage.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/v1/api")
public class AuthController {
    @Autowired
    private AccountService account;
    @PostMapping(path = "/login")
    public ResponseEntity login(HttpServletRequest request){
        return new ResponseEntity("Login Successfully!", HttpStatus.OK);
    }

    @PostMapping("/logout")
    public String logout(){
        return "Logout Successfully";
    }

}
