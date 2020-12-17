package binhtt.dev.manage.supporters;

import binhtt.dev.manage.entities.Account;
import binhtt.dev.manage.entities.Notification;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;

@Data
@Entity
@Table(name = "tbl_noti_user")
public class NotificationUser {
    @Id
    @Column(name = "id", nullable = false, unique = true, updatable = false)
    private String id;
    @Column(name = "status")
    private int status;

    @ManyToOne
    @JoinColumn(name = "studentID", nullable = false)
    private Account account;

    @ManyToOne
    @JoinColumn(name = "noyificationId", nullable = false)
    private Notification notification;

}
