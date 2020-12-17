package binhtt.dev.manage.entities;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@Table(name = "tbl_group")
@Data
public class Group implements Serializable {
    @Id
    @Column(name = "groupId", nullable = false, updatable = false, unique = true)
    private @Max(50) String groupId;
    @Column(name = "groupName", nullable = false)
    private @Min(10) @Max(100) String groupName;
    private String groupDesc;
    @Column(name = "createdTime")
    private Timestamp createdTime;
    @Column(name = "numOfMems")
    private int numOfMems;
    @Column(name = "status")
    private int status;
    @ManyToOne
    @JoinColumn(name = "leaderId", nullable = false)
    private Account leader;
}
