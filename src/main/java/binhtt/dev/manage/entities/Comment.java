package binhtt.dev.manage.entities;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;
import javax.validation.constraints.Email;
import java.io.Serializable;
import java.sql.Timestamp;

@Data
@Entity
@Table(name = "tbl_comment")
public class Comment implements Serializable {
    @Id
    @Column(name = "commentId", nullable = false, updatable = false, unique = true)
    private String commentId;
    @Column(name = "content", nullable = false)
    private String content;
    @Column(name = "createdTime", nullable = false)
    private Timestamp createdTime;
    @Column(name = "status")
    private int status;
    @Column(name = "studentID", nullable = false)
    private String studentID;
    @Column(name = "fullname", nullable = false)
    private String fullname;
    @Column(name = "email", nullable = false)
    private @Email String email;
    @ManyToOne
    @JoinColumn(name = "parentId")
    private Comment parent;
    @ManyToOne
    @JoinColumn(name = "eventId")
    private Event event;
}
