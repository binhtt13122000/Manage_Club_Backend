package binhtt.dev.manage.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "tbl_role")
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Role implements Serializable {
    @Id
    @Column(name = "roleId")
    private int roleId;
    @Column(name = "roleName")
    private String roleName;
}
