package binhtt.dev.manage.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;

@Entity
@Table(name = "tbl_user")
@Data
public class Account implements Serializable {
    @Id
    @Column(name = "studentID", nullable = false, unique = true, updatable = false)
    private String studentID;
    @JsonIgnore
    @Column(name = "password", nullable = false)
    private String password;
    @Column(name = "fullname", nullable = false)
    private @NotNull @NotBlank String fullname;
    @Column(name = "avatarId")
    private String avatarId;
    @Column(name = "phone")
    private @Max(10) String phone;
    @Column(name = "email", unique = true)
    private @NotNull @NotBlank @Email String email;
    @Column(name = "status")
    private boolean status;
    @ManyToOne
    @JoinColumn(name = "roleId", nullable = false)
    @JsonIgnore
    private Role role;

    @Transient
    private int roleId;

    public int getRoleId() {
        return (roleId > 0) ? roleId : role.getRoleId();
    }


}
