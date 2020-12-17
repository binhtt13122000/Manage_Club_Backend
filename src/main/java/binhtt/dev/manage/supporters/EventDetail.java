package binhtt.dev.manage.supporters;

import binhtt.dev.manage.entities.Event;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;
import javax.validation.constraints.Email;
import java.sql.Timestamp;

@Entity
@Data
@Table(name = "tbl_event_detail")
public class EventDetail {
    @Id
    @Column(name = "eventDetailId", nullable = false, updatable = false, unique = true)
    private String eventDetailId;
    @Column(name = "status")
    private int status;
    @Column(name = "registeredTime", nullable = false)
    private Timestamp registeredTime;
    @Column(name = "attendanceTime", nullable = false)
    private Timestamp attendanceTime;
    @Column(name = "feedback")
    private String feedback;
    @Column(name = "rate")
    private String rate;
    @Column(name = "studentID", nullable = false)
    private String studentID;
    @Column(name = "fullname", nullable = false)
    private String fullname;
    @Column(name = "email", nullable = false)
    private @Email String email;

    @ManyToOne
    @JoinColumn(name = "eventId", nullable = false)
    private Event event;
}
