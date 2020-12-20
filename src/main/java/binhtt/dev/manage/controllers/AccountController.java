package binhtt.dev.manage.controllers;

import binhtt.dev.manage.entities.Account;
import binhtt.dev.manage.services.AccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/api")
@Tag(name = "Account")
public class AccountController {
    @Autowired
    private AccountService accountService;

    String getRole(Authentication authentication){
        List<String> roles = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
        return roles.get(0);    }
    //create
    @Operation(description = "Add new user (ADMIN)", responses = {
            @ApiResponse(
                    description = "Add new User Successfully!",
                    responseCode = "200",
                    content = @Content(
                            mediaType = "text/plain; charset=utf-8",
                            examples = @ExampleObject(
                                    description = "Add new User Successfully!",
                                    value = "Add new User Successfully!"
                            ),
                            schema = @Schema(implementation = Account.class)
                    )
            ),
            @ApiResponse(
                    description = "Access denied!",
                    responseCode = "403",
                    content = @Content(
                            mediaType = "text/plain; charset=utf-8",
                            examples = @ExampleObject(
                                    description = "Access denied!",
                                    value = "Access denied!"
                            ),
                            schema = @Schema(implementation = String.class)
                    )
            ),
            @ApiResponse(
                    description = "StudentID or Email is taken!",
                    responseCode = "400",
                    content = @Content(
                            mediaType = "text/plain; charset=utf-8",
                            examples = @ExampleObject(
                                    description = "StudentID or Email is taken!",
                                    value = "StudentID or Email is taken!"
                            ),
                            schema = @Schema(implementation = String.class)
                    )
            ),
            @ApiResponse(
                    description = "Constraints are invalid!!",
                    responseCode = "500",
                    content = @Content(
                            mediaType = "text/plain; charset=utf-8",
                            examples = @ExampleObject(
                                    description = "Constraints are invalid!!",
                                    value = "Constraints are invalid!!"
                            ),
                            schema = @Schema(implementation = String.class)
                    )
            ),
    })
    @PostMapping("/users")
    public ResponseEntity addMemberToGroup(@RequestBody Account account, Authentication authentication) {
        try {
            String role = getRole(authentication);
            if(!role.equals("ROLE_ADMIN")){
                return new ResponseEntity("Access denied!", HttpStatus.FORBIDDEN);
            }
            if(accountService.findAccountById(account.getStudentID()) != null || accountService.findAccountByEmail(account.getEmail()) != null){
                return new ResponseEntity("StudentID or Email is taken!", HttpStatus.BAD_REQUEST);
            }
            accountService.addMember(account);
            return new ResponseEntity("Add new User Successfully!", HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity("Constraints are invalid!!", HttpStatus.INTERNAL_SERVER_ERROR);
        }
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
            @RequestParam Optional<String> q, Authentication authentication) {
        Pageable pageable = PageRequest.of(offset.orElse(0), limit.orElse(10), Sort.Direction.ASC, sort.orElse("studentID"));
        Page<Account> accounts;
        authentication.getAuthorities().forEach(e -> System.out.println(e));
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
        Account account = accountService.findAccountById(studentId);
        if(account == null){
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity(account, HttpStatus.OK);
        }
    }

}
