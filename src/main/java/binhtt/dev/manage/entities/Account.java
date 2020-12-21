package binhtt.dev.manage.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    //studentId
    @Id
    @Column(name = "studentID", nullable = false, unique = true, updatable = false)
    @NotNull
    @NotBlank
    @Size(max = 8, min = 8)
    @Schema(example = "SE140125")
    private String studentID;
    //password
    @JsonIgnore
    @Column(name = "password", nullable = false)
    private String password;
    //fullname
    @Column(name = "fullname", nullable = false)
    @NotNull
    @NotBlank
    @Size(max = 50)
    @Schema(example = "Trương Thanh Bình")
    private String fullname;
    //avatar
    @Column(name = "avatarId")
    private String avatarId;
    //phone
    @Column(name = "phone")
    @Size(max = 10, min = 9)
    @Schema(example = "0335579880")
    private String phone;
    //email
    @Column(name = "email", unique = true)
    @NotNull
    @NotBlank
    @Email
    @Size(max = 50)
    @Schema(example = "binhttse140125@fpt.edu.vn")
    private String email;
    //status
    @Column(name = "status")
    @Schema(example = "true")
    private boolean status;

    //relation
    @ManyToOne
    @JoinColumn(name = "roleId", nullable = false)
    @JsonIgnore
    private Role role;

    @Transient
    private int roleId;

    public int getRoleId() {
        return (role != null) ? role.getRoleId() : 0;
    }
}
