package binhtt.dev.manage.entities;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@Data
@Table(name = "tbl_notification")
public class Notification implements Serializable {
    @Id
    @Column(name = "notificationId", nullable = false, unique = true, updatable = false)
    private String notificationId;
    @Column(name = "content", nullable = false)
    private String content;
    @Column(name = "createdTime", nullable = false)
    private Timestamp createdTime;
}
