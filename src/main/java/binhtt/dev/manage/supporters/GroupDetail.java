package binhtt.dev.manage.supporters;

import binhtt.dev.manage.entities.Account;
import binhtt.dev.manage.entities.Group;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;

@Entity
@Table(name = "tbl_group_detail")
@Data
public class GroupDetail {
    @Id
    @Column(name = "groupDetailId", nullable = false, unique = true, updatable = false)
    private String groupDetailId;
    @Column(name = "status")
    private int status;

    @ManyToOne
    @JoinColumn(name = "groupId", nullable = false)
    private Group group;

    @ManyToOne
    @JoinColumn(name = "studentID", nullable = false)
    private Account account;

}
