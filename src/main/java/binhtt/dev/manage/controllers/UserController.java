package binhtt.dev.manage.controllers;

import binhtt.dev.manage.entities.UserEntity;
import binhtt.dev.manage.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("v1/api")
public class UserController {
    @Autowired
    private UserService userService;
    //create
    @PostMapping("/users")
    public ResponseEntity addMemberToGroup(@RequestBody UserEntity account){
        return null;
    }

    //ban user
    @PutMapping("users/{studentId}/block_account")
    public ResponseEntity blockAccount(@PathVariable("studentId") String studentId){
        return null;
    }

    //update profile
    @PutMapping("/users/{studentId}")
    public ResponseEntity updateProfile(@PathVariable("studentId") String studentId, @RequestBody UserEntity userEntity){
        return null;
    }

    //changePassword
    @PutMapping("/users/{studentId}/change_password")
    public ResponseEntity changePassword(@PathVariable("studentId") String studentId){
        return null;
    }

    //get users
    @GetMapping("/users")
    public ResponseEntity getUsers(){
        return null;
    }

    //get users by id
    @GetMapping("/users/{studentId}")
    public ResponseEntity getUserById(@PathVariable("studentId") String studentId){
        return null;
    }


}
