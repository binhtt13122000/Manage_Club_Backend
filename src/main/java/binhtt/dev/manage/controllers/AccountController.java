package binhtt.dev.manage.controllers;

import binhtt.dev.manage.entities.Account;
import binhtt.dev.manage.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@RestController
@RequestMapping("v1/api")
public class AccountController {
    @Autowired
    private AccountService accountService;

    //create
    @PostMapping("/users")
    @RolesAllowed("ROLE_ADMIN")
    public ResponseEntity addMemberToGroup(@RequestBody Account account) {
        if (accountService.findAccountById(account.getStudentID()) != null) {
            return new ResponseEntity("StudentID is taken", HttpStatus.BAD_REQUEST);
        }
        if (accountService.findAccountByEmail(account.getEmail()) != null) {
            return new ResponseEntity("Email is taken", HttpStatus.BAD_REQUEST);
        }
        boolean check = accountService.addMember(account);
        if (check) {
            return new ResponseEntity("Create Successfully", HttpStatus.CREATED);
        }
        return new ResponseEntity("Add user failed!", HttpStatus.BAD_REQUEST);
    }

    //ban user
    @PutMapping("users/{studentId}/block_account")
    @RolesAllowed("ROLE_ADMIN")
    public ResponseEntity blockAccount(@PathVariable("studentId") String studentId) {
        Account account = accountService.findAccountById(studentId);
        if (account == null) {
            return new ResponseEntity("Not found!", HttpStatus.NOT_FOUND);
        } else {
            account.setStatus(false);
            return new ResponseEntity("Successfully!", HttpStatus.OK);
        }
    }

    //update profile
    @PutMapping("/users/{studentId}")
    public ResponseEntity updateProfile(@PathVariable("studentId") String studentId, @RequestBody Account account, Authentication authentication) {
        if (studentId.equals(authentication.getName())) {
            Account currentAccount = accountService.findAccountById(studentId);
            if (currentAccount == null) {
                return new ResponseEntity("Not Found!", HttpStatus.NOT_FOUND);
            } else {
                accountService.updateProfile(currentAccount, account);
                return new ResponseEntity("Update Successfully!", HttpStatus.OK);
            }
        }
        return new ResponseEntity("Access deny!!", HttpStatus.FORBIDDEN);
    }

    //changePassword
    @PutMapping("/users/{studentId}/change_password")
    public ResponseEntity changePassword(@PathVariable("studentId") String studentId, HttpServletRequest request, Authentication authentication) {
        if (studentId.equals(authentication.getName())) {
            Account currentAccount = accountService.findAccountById(studentId);
            if (currentAccount == null) {
                return new ResponseEntity("Not Found!", HttpStatus.NOT_FOUND);
            } else {
                String newPassword = request.getParameter("newPassword");
                String oldPassword = request.getParameter("oldPassword");
                accountService.changePassword(currentAccount, newPassword, oldPassword);
                return new ResponseEntity("Change Password Successfully!", HttpStatus.OK);
            }
        }
        return new ResponseEntity("Access deny!!", HttpStatus.FORBIDDEN);
    }

    //get users
    @GetMapping("/users")
    @RolesAllowed("ROLE_ADMIN")
    public ResponseEntity getUsers(
            @RequestParam Optional<Integer> offset,
            @RequestParam Optional<Integer> limit,
            @RequestParam Optional<String> sort,
            @RequestParam Optional<String> q) {
        Pageable pageable = PageRequest.of(offset.orElse(0), limit.orElse(10), Sort.Direction.ASC, sort.orElse("studentId"));
        Page<Account> accounts;
        if(q.isPresent()){
            accounts = accountService.findByName(q.get(), pageable);
        } else {
            accounts = accountService.findAllAccount(pageable);
        }
        if(accounts.isEmpty()){
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity(accounts, HttpStatus.OK);
        }
    }

    //get users by id
    @GetMapping("/users/{studentId}")
    public ResponseEntity getUserById(@PathVariable("studentId") String studentId) {
        return null;
    }


}
