package binhtt.dev.manage.controllers;

import binhtt.dev.manage.entities.Account;
import binhtt.dev.manage.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("v1/api")
public class AccountController {
    @Autowired
    private AccountService accountService;
    //create
    @PostMapping("/users")
    public ResponseEntity addMemberToGroup(@RequestBody Account account){
        if(accountService.findAccountById(account.getStudentID()) != null){
            return new ResponseEntity("StudentID is taken", HttpStatus.BAD_REQUEST);
        }
        if(accountService.findAccountByEmail(account.getEmail()) != null){
            return new ResponseEntity("Email is taken", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity("Create Successfully", HttpStatus.CREATED);
    }

    //ban user
    @PutMapping("users/{studentId}/block_account")
    public ResponseEntity blockAccount(@PathVariable("studentId") String studentId){
        return null;
    }

    //update profile
    @PutMapping("/users/{studentId}")
    public ResponseEntity updateProfile(@PathVariable("studentId") String studentId, @RequestBody Account userEntity){
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
