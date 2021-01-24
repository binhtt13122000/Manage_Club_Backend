package binhtt.dev.manage.controllers;

import binhtt.dev.manage.entities.Account;
import binhtt.dev.manage.entities.Role;
import binhtt.dev.manage.services.AccountService;
import binhtt.dev.manage.utils.ApiError;
import binhtt.dev.manage.utils.RoleUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
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
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Data
class ChangePassRequest {
    @NotNull(message = "new password is required!")
    @Size(min = 10, max = 20, message = "New Password is from 10 to 20 characters!")
    private String newPassword;
    @NotNull(message = "password is required!")
    @Size(min = 10, max = 20, message = "Password is from 10 to 20 characters!")
    private String oldPassword;
    @NotNull(message = "Confirm message is required!")
    @Size(min = 10, max = 20, message = "Confirm Password is from 10 to 20 characters!")
    private String confirmPassword;
}
@RestController
@RequestMapping("/v1/api")
@Tag(name = "Account")
public class AccountController {
    @Autowired
    private AccountService accountService;

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
                    description = "Constraints are invalid!!",
                    responseCode = "400",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class)
                    )
            ),
    })
    @PostMapping("/users")
    public ResponseEntity addMemberToGroup(@Valid @RequestBody Account account, Authentication authentication) {
        System.out.println(RoleUtils.isAdmin(authentication));
        if(RoleUtils.isAdmin(authentication)){
            accountService.addMember(account);
            return new ResponseEntity("Add new User Successfully!", HttpStatus.OK);
        }
        return new ResponseEntity("Access denied!", HttpStatus.FORBIDDEN);
    }

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
                    description = "Ban already!",
                    responseCode = "400",
                    content = @Content(
                            mediaType = "text/plain; charset=utf-8",
                            examples = @ExampleObject(
                                    description = "Ban already!",
                                    value = "Ban already!"
                            ),
                            schema = @Schema(implementation = String.class)
                    )
            ),
    })
    @PutMapping("users/{studentId}/block_account")
    public ResponseEntity blockAccount(@PathVariable("studentId") String studentId, Authentication authentication) {
        if(RoleUtils.isAdmin(authentication)){
            Account currentAccount = accountService.findAccountById(studentId);
            if(currentAccount == null){
                return new ResponseEntity("StudentID is not found!", HttpStatus.NOT_FOUND);
            }
            if(currentAccount.isStatus()){
                accountService.banAccount(currentAccount);
                return new ResponseEntity("Ban User Successfully!", HttpStatus.OK);
            }
            return new ResponseEntity("Ban already!", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity("Access denied!", HttpStatus.FORBIDDEN);
    }

    @Operation(description = "Active user (ADMIN)", responses = {
            @ApiResponse(
                    description = "Active User Successfully!",
                    responseCode = "200",
                    content = @Content(
                            mediaType = "text/plain; charset=utf-8",
                            examples = @ExampleObject(
                                    description = "Active User Successfully!",
                                    value = "Active User Successfully!"
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
                    description = "Active already!",
                    responseCode = "400",
                    content = @Content(
                            mediaType = "text/plain; charset=utf-8",
                            examples = @ExampleObject(
                                    description = "Active already!",
                                    value = "Active already!"
                            ),
                            schema = @Schema(implementation = String.class)
                    )
            ),
    })
    @PutMapping("users/{studentId}/active_account")
    public ResponseEntity activeAccount(@PathVariable("studentId") String studentId, Authentication authentication) {
        if(RoleUtils.isAdmin(authentication)){
            Account currentAccount = accountService.findAccountById(studentId);
            if(currentAccount == null){
                return new ResponseEntity("StudentID is not found!", HttpStatus.NOT_FOUND);
            }
            if(currentAccount.isStatus()){
                return new ResponseEntity("Active already!", HttpStatus.BAD_REQUEST);
            }
            accountService.activeAccount(currentAccount);
            return new ResponseEntity("Active User Successfully!", HttpStatus.OK);
        }
        return new ResponseEntity("Access denied!", HttpStatus.FORBIDDEN);
    }

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
                    responseCode = "400",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class)
                    )
            ),
    })
    @PutMapping("/users/{studentId}")
    public ResponseEntity updateProfile(@PathVariable("studentId") String studentId, @Valid @RequestBody Account account, Authentication authentication) {
        if(RoleUtils.isMySelf(authentication, studentId)){
            Account currentAccount = accountService.findAccountById(studentId);
            if(currentAccount == null){
                return new ResponseEntity("StudentID is not found!", HttpStatus.NOT_FOUND);
            }
            accountService.updateProfile(currentAccount, account);
        }
        return new ResponseEntity("Access denied!", HttpStatus.FORBIDDEN);
    }

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
                    description = "Change failed!",
                    responseCode = "400",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class)
                    )
            ),
    })
    @PutMapping("/users/{studentId}/change_password")
    public ResponseEntity changePassword(@PathVariable("studentId") String studentId, @RequestBody @Valid ChangePassRequest request, Authentication authentication) {
        String newPassword = request.getNewPassword();
        String oldPassword = request.getOldPassword();
        String confirmPassword = request.getConfirmPassword();
        List<String> errors = new ArrayList<>();
        if(!newPassword.equals(confirmPassword)){
            errors.add("New password and confirm password is not equals!");
        }
        if(newPassword.equals(oldPassword)){
            errors.add("Password and new password is equals!");
        }
        if(errors.size() > 0){
            ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, "Bad request", errors);
            return new ResponseEntity(apiError, apiError.getStatus());
        }
        if(RoleUtils.isMySelf(authentication, studentId)){
            Account currentAccount = accountService.findAccountById(studentId);
            if(currentAccount != null){
                accountService.changePassword(currentAccount, request.getNewPassword(), request.getOldPassword());
            }
            return new ResponseEntity("Change Password Successfully!", HttpStatus.OK);
        }
        return new ResponseEntity("Access denied!", HttpStatus.FORBIDDEN);
    }

    @Operation(description = "Get All Student", responses = {
            @ApiResponse(
                    description = "Get All Successfully!",
                    responseCode = "200",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = Account.class))
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
            @RequestParam Optional<String> studentID,
            @RequestParam Optional<String> fullname,
            @RequestParam Optional<String> email, Authentication authentication) {
        List<Account> accounts = null;
        if(RoleUtils.isLeader(authentication)){
            accounts =  accountService.findAccountByProperties(studentID.orElse(""), fullname.orElse(""), email.orElse(""), false, true);
        }
        if(RoleUtils.isAdmin(authentication)){
            accounts =  accountService.findAccountByProperties(studentID.orElse(""), fullname.orElse(""), email.orElse(""), true, true);
        }
        if(accounts == null){
            return new ResponseEntity("Access denied!", HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity(accounts, HttpStatus.OK);
    }

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
        if(RoleUtils.isMySelf(authentication, studentId)){
            Account account = accountService.findAccountById(studentId);
            if(account == null){
                return new ResponseEntity("StudentID is not found!", HttpStatus.NOT_FOUND);
            } else {
                return new ResponseEntity(account, HttpStatus.OK);
            }
        }
        return new ResponseEntity("Access denied!", HttpStatus.FORBIDDEN);
    }

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
        if(RoleUtils.isAdmin(authentication)){
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
        return new ResponseEntity("Access denied!", HttpStatus.FORBIDDEN);
    }

}
