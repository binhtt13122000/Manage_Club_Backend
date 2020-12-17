package binhtt.dev.manage.entities;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@Table(name = "tbl_event")
@Data
public class Event implements Serializable {
    @Id
    @Column(name = "eventId", nullable = false, unique = true, updatable = false)
    private @Max(50) String eventId;
    @Column(name = "eventName", nullable = false)
    private @Min(20) @Max(100) String eventName;
    @Column(name = "eventDesc")
    private String eventDesc;
    @Column(name = "createdTime", nullable = false)
    private Timestamp createdTime;
    @Column(name = "finishRegisterTime", nullable = false)
    private Timestamp finishRegisterTime;
    @Column(name = "startTime", nullable = false)
    private Timestamp startTime;
    @Column(name = "endTime", nullable = false)
    private Timestamp endTime;
    @Column(name = "total")
    private int total;
    @Column(name = "numOfAttendance")
    private int numOfAttendance;
    @Column(name = "isInteral")
    private boolean isInternal;
    @Column(name = "speaker")
    private String speaker;
    @Column(name = "status")
    private int status;
    @Column(name = "banner", nullable = false)
    private String banner;
}
