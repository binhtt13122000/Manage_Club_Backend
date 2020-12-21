package binhtt.dev.manage.controllers;

import binhtt.dev.manage.entities.Account;
import binhtt.dev.manage.entities.Role;
import binhtt.dev.manage.services.AccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Data;
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
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Data
class ChangePassRequest {
    private String newPassword;
    private String oldPassword;
}
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
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    description = "Add new User Successfully!",
                                    value = "Add new User Successfully!"
                            ),
                            schema = @Schema(implementation = String.class)
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
    @Operation(description = "Ban user (ADMIN)", responses = {
            @ApiResponse(
                    description = "Ban User Successfully!",
                    responseCode = "200",
                    content = @Content(
                            mediaType = "text/plain; charset=utf-8",
                            examples = @ExampleObject(
                                    description = "Ban User Successfully!",
                                    value = "Ban User Successfully!"
                            ),
                            schema = @Schema(implementation = String.class)
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
                    description = "StudentID is not found!",
                    responseCode = "404",
                    content = @Content(
                            mediaType = "text/plain; charset=utf-8",
                            examples = @ExampleObject(
                                    description = "StudentID is not found!",
                                    value = "StudentID is not found!"
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
    @PutMapping("users/{studentId}/block_account")
    public ResponseEntity blockAccount(@PathVariable("studentId") String studentId, Authentication authentication) {
        try {
            String role = getRole(authentication);
            if(!role.equals("ROLE_ADMIN")){
                return new ResponseEntity("Access denied!", HttpStatus.FORBIDDEN);
            }
            Account account = accountService.findAccountById(studentId);
            if (account == null) {
                return new ResponseEntity("StudentID is not found!", HttpStatus.NOT_FOUND);
            } else {
                account.setStatus(false);
                return new ResponseEntity("Ban User Successfully!", HttpStatus.OK);
            }
        } catch (Exception e){
            return new ResponseEntity("Constraints are invalid!!", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //update profile
    @Operation(description = "Update profile", responses = {
            @ApiResponse(
                    description = "Update Profile Successfully!",
                    responseCode = "200",
                    content = @Content(
                            mediaType = "text/plain; charset=utf-8",
                            examples = @ExampleObject(
                                    description = "Update Profile Successfully!",
                                    value = "Update Profile Successfully!"
                            ),
                            schema = @Schema(implementation = String.class)
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
                    description = "StudentID is not found!",
                    responseCode = "404",
                    content = @Content(
                            mediaType = "text/plain; charset=utf-8",
                            examples = @ExampleObject(
                                    description = "StudentID is not found!",
                                    value = "StudentID is not found!"
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
    @PutMapping("/users/{studentId}")
    public ResponseEntity updateProfile(@PathVariable("studentId") String studentId, @RequestBody Account account, Authentication authentication) {
        if (studentId.equals(authentication.getName())) {
            try {
                Account currentAccount = accountService.findAccountById(studentId);
                if (currentAccount == null) {
                    return new ResponseEntity("StudentID is not found!", HttpStatus.NOT_FOUND);
                } else {
                    accountService.updateProfile(currentAccount, account);
                    return new ResponseEntity("Update Profile Successfully!", HttpStatus.OK);
                }
            } catch (Exception e){
                return new ResponseEntity("Constraints are invalid!!", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        return new ResponseEntity("Access denied!", HttpStatus.FORBIDDEN);
    }

    //changePassword
    @Operation(description = "Change password", responses = {
            @ApiResponse(
                    description = "Change Password Successfully!",
                    responseCode = "200",
                    content = @Content(
                            mediaType = "text/plain; charset=utf-8",
                            examples = @ExampleObject(
                                    description = "Change Password Successfully!",
                                    value = "Change Password Successfully!"
                            ),
                            schema = @Schema(implementation = String.class)
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
                    description = "StudentID is not found!",
                    responseCode = "404",
                    content = @Content(
                            mediaType = "text/plain; charset=utf-8",
                            examples = @ExampleObject(
                                    description = "StudentID is not found!",
                                    value = "StudentID is not found!"
                            ),
                            schema = @Schema(implementation = String.class)
                    )
            ),
            @ApiResponse(
                    description = "New password is similar to Old password!",
                    responseCode = "400",
                    content = @Content(
                            mediaType = "text/plain; charset=utf-8",
                            examples = @ExampleObject(
                                    description = "New password is similar to Old password!",
                                    value = "New password is similar to Old password!"
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
    @PutMapping("/users/{studentId}/change_password")
    public ResponseEntity changePassword(@PathVariable("studentId") String studentId, @RequestBody ChangePassRequest request, Authentication authentication) {
        if (studentId.equals(authentication.getName())) {
            try {
                Account currentAccount = accountService.findAccountById(studentId);
                if (currentAccount == null) {
                    return new ResponseEntity("StudentID is not found!", HttpStatus.NOT_FOUND);
                } else {
                    String newPassword = request.getNewPassword();
                    String oldPassword = request.getOldPassword();
                    if(oldPassword.trim().equalsIgnoreCase(newPassword.trim())){
                        return new ResponseEntity("New password is similar to Old password!", HttpStatus.BAD_REQUEST);
                    }
                    accountService.changePassword(currentAccount, newPassword, oldPassword);
                    return new ResponseEntity("Change Password Successfully!", HttpStatus.OK);
                }
            } catch (Exception e){
                return new ResponseEntity("Constraints are invalid!!", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        return new ResponseEntity("Access denied!", HttpStatus.FORBIDDEN);
    }

    //get users
    @Operation(description = "Get All Student", responses = {
            @ApiResponse(
                    description = "Get All Successfully!",
                    responseCode = "200",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Page.class)
                    )
            ),
            @ApiResponse(
                    description = "No data!",
                    responseCode = "404",
                    content = @Content(
                            mediaType = "text/plain; charset=utf-8",
                            examples = @ExampleObject(
                                    description = "No data!",
                                    value = "No data!"
                            ),
                            schema = @Schema(implementation = String.class)
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
    })
    @GetMapping("/users")
    public ResponseEntity getUsers(
            @RequestParam Optional<Integer> offset,
            @RequestParam Optional<Integer> limit,
            @RequestParam Optional<String> sort,
            @RequestParam Optional<Integer> roleId,
            @RequestParam Optional<String> q, Authentication authentication) {
        if(!getRole(authentication).equals("ROLE_ADMIN")){
            return new ResponseEntity("Access denied!", HttpStatus.FORBIDDEN);
        }
        Pageable pageable = PageRequest.of(offset.orElse(0), limit.orElse(10), Sort.Direction.ASC, sort.orElse("studentID"));
        Page<Account> accounts;
        if(q.isPresent()){
            accounts = accountService.findByName(q.get(), pageable);
        } else {
            accounts = accountService.findAllAccount(pageable);;
        }
        if(accounts.isEmpty()){
            return new ResponseEntity("No data",HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity(accounts, HttpStatus.OK);
        }
    }

    //get users by id
    @Operation(description = "Get One Student", responses = {
            @ApiResponse(
                    description = "Get One Successfully!",
                    responseCode = "200",
                    content = @Content(
                            mediaType = "application/json",
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
                    description = "StudentID is not found!",
                    responseCode = "404",
                    content = @Content(
                            mediaType = "text/plain; charset=utf-8",
                            examples = @ExampleObject(
                                    description = "StudentID is not found!",
                                    value = "StudentID is not found!"
                            ),
                            schema = @Schema(implementation = String.class)
                    )
            ),
    })
    @GetMapping("/users/{studentId}")
    public ResponseEntity getUserById(@PathVariable("studentId") String studentId, Authentication authentication) {
        if(!authentication.getName().equals(studentId) && !getRole(authentication).equals("ROLE_ADMIN")){
            return new ResponseEntity("Access denied", HttpStatus.FORBIDDEN);
        }
        Account account = accountService.findAccountById(studentId);
        if(account == null){
            return new ResponseEntity("StudentID is not found!", HttpStatus.NOT_FOUND);
        } else {
            System.out.println();
            return new ResponseEntity(account, HttpStatus.OK);
        }
    }

    //change role
    @Operation(description = "Change role (ADMIN)", responses = {
            @ApiResponse(
                    description = "Change role Successfully!",
                    responseCode = "200",
                    content = @Content(
                            mediaType = "text/plain; charset=utf-8",
                            examples = @ExampleObject(
                                    description = "Change role Successfully!",
                                    value = "Change role Successfully!"
                            ),
                            schema = @Schema(implementation = String.class)
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
                    description = "StudentID is not found!",
                    responseCode = "404",
                    content = @Content(
                            mediaType = "text/plain; charset=utf-8",
                            examples = @ExampleObject(
                                    description = "StudentID is not found!",
                                    value = "StudentID is not found!"
                            ),
                            schema = @Schema(implementation = String.class)
                    )
            ),
            @ApiResponse(
                    description = "Cannot change!",
                    responseCode = "400",
                    content = @Content(
                            mediaType = "text/plain; charset=utf-8",
                            examples = @ExampleObject(
                                    description = "Cannot change!",
                                    value = "Cannot change!"
                            ),
                            schema = @Schema(implementation = String.class)
                    )
            ),
    })
    @PutMapping("/users/{studentId}/upgrade_role")
    public ResponseEntity changeRole(@PathVariable("studentId") String studentId, Authentication authentication){
        if(!getRole(authentication).equals("ROLE_ADMIN")){
            return new ResponseEntity("Access denied", HttpStatus.FORBIDDEN);
        }
        Account account = accountService.findAccountById(studentId);
        if (account == null) {
            return new ResponseEntity("StudentID is not found!", HttpStatus.NOT_FOUND);
        } else {
            if(account.getRoleId() != 1){
                return new ResponseEntity("Cannot change", HttpStatus.BAD_REQUEST);
            }
            account.setRole(new Role(2, null));
            return new ResponseEntity("Change role Successfully!", HttpStatus.OK);
        }
    }

}
