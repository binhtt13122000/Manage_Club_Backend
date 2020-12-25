package binhtt.dev.manage.entities;

import binhtt.dev.manage.utils.Regex;
import binhtt.dev.manage.validation.Unique;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;

@Entity
@Table(name = "tbl_user")
@Data
@Schema(name = "Account")
public class Account implements Serializable {
    @Id
    @Unique(className = "Account", fieldName = "studentID", message = "StudentID is taken!")
    @Column(name = "studentID", nullable = false, unique = true, updatable = false)
    @NotNull(message = "Student is not null!")
    @Size(max = 8, min = 8, message = "StudentID has 8 characters!")
    @Pattern(regexp = Regex.REGEX_STUDENT_ID, message = "StudentID is not correct format!")
    @Schema(example = "SE140125")
    private String studentID;

    @Column(name = "password", nullable = false)
    @NotNull(message = "Password is not null")
    private String password;

    @Column(name = "fullname", nullable = false)
    @NotNull(message = "Fullname is not null!")
    @Size(max = 50, min = 10,message = "Message has from 10 to 50 characters!")
    @Schema(example = "Trương Thanh Bình")
    @Pattern(regexp = Regex.REGEX_FULL_NAME, message = "Fullname is not valid!")
    private String fullname;

    @Column(name = "avatarId")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String avatarId;

    @Column(name = "phone")
    @Size(max = 10, min = 9)
    @Pattern(regexp = Regex.REGEX_PHONE, message = "Phone number is wrong format!")
    @Schema(example = "0335579880")
    private String phone;

    @Column(name = "email", unique = true)
    @Unique(className = "Account", fieldName = "email", message = "Email is taken!")
    @NotNull(message = "Email is not null")
    @Email(message = "Email is wrong format!")
    @Size(max = 50, min = 10, message = "Email has 10 to 50 characters!")
    @Schema(example = "binhttse140125@fpt.edu.vn")
    private String email;

    @Column(name = "status")
    @Schema(example = "true")
    private boolean status;

    @ManyToOne
    @JoinColumn(name = "roleId", nullable = false)
    @JsonIgnore
    private Role role;

    @Transient
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private int roleId;

    public int getRoleId() {
        return (role != null) ? role.getRoleId() : 0;
    }
}
