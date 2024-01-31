package com.blubank.doctorappointment.models.entities;

import com.blubank.doctorappointment.bases.BaseEntity;
import com.blubank.doctorappointment.enums.AppointmentStatus;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;

@Builder
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Entity
@Table(name = "APPOINTMENTS")
public class Appointment extends BaseEntity {
    private static final long serialVersionUID = -2817507226793734955L;

    @Column
//    @Temporal(TemporalType.TIMESTAMP)
    private Date commenceTime;
    @Column
    private Integer length;
    @Column
    private AppointmentStatus status;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient")
    private Patient patient;

    @Transient
    public LocalDate getLocalDate() {
        return this.commenceTime.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }

    @Transient
    public LocalTime getStartLocalTime() {
        return this.commenceTime.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalTime();
    }

    @Transient
    public LocalTime getEndTime() {
        return getStartLocalTime().plusMinutes(this.length);
    }
}
