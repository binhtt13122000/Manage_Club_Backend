package binhtt.dev.manage.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;
import javax.validation.constraints.*;
import java.io.Serializable;

@Entity
@Table(name = "tbl_user")
@Data
public class Account implements Serializable {
    @Id
    @Column(name = "studentID", nullable = false, unique = true, updatable = false)
    private @Max(8) String studentID;
    @JsonIgnore
    @Column(name = "password", nullable = false)
    private String password;
    @Column(name = "fullname", nullable = false)
    private @NotNull @NotBlank @Min(10) @Max(50) String fullname;
    @Column(name = "avatarId")
    private String avatarId;
    @Column(name = "phone")
    private @Max(10) String phone;
    @Column(name = "email", unique = true)
    private @NotNull @NotBlank @Min(10) @Max(50) @Email String email;
    @Column(name = "status")
    private boolean status;
    @ManyToOne
    @JoinColumn(name = "roleId", nullable = false)
    private Role role;
}
