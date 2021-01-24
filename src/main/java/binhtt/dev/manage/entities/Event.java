package binhtt.dev.manage.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@Table(name = "tbl_event")
@Data
public class Event implements Serializable {
    @Id
    @Column(name = "eventId", nullable = false, unique = true, updatable = false)
    private String eventId;
    @Column(name = "eventName", nullable = false)
    @NotNull(message = "event name is not null")
    @Size(min = 10, max = 100, message = "Event name has from 10 to 100 characters!")
    private String eventName;
    @Column(name = "eventDesc")
    private String eventDesc;
    @Column(name = "createdTime", nullable = false)
    private Timestamp createdTime;
    @Column(name = "finishRegisterTime", nullable = false)
    @NotNull(message = "Time to finish register is not null!")
    private Timestamp finishRegisterTime;
    @Column(name = "startTime", nullable = false)
    @NotNull(message = "Start time is not null!")
    private Timestamp startTime;
    @Column(name = "endTime", nullable = false)
    @NotNull(message = "End time is not null!")
    private Timestamp endTime;
    @Column(name = "total")
    @Min(value = 1, message = "Total is a number greater than 1!")
    @NotNull(message = "total is not null!")
    private int total;
    @Column(name = "numOfAttendance")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private int numOfAttendance;
    @Column(name = "isInteral")
    @NotNull(message = "isInternal is not null!")
    private boolean isInternal;
    @Column(name = "speaker")
    @Size(min = 10, max = 50, message = "speaker name is between 10 to 50 characters!")
    private String speaker;
    @Column(name = "status")
    @NotNull(message = "status is not null!")
    private int status;
    @Column(name = "banner", nullable = false)
    @NotNull(message = "Banner is not null!")
    private String banner;
}
