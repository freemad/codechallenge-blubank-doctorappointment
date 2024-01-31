package com.blubank.doctorappointment.models.dtos;

import com.blubank.doctorappointment.bases.BaseDto;
import com.blubank.doctorappointment.enums.AppointmentStatus;
import com.blubank.doctorappointment.models.entities.Appointment;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@Data
@Builder
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class AppointmentDto extends BaseDto<Appointment> {
    private static final long serialVersionUID = 8248594636161608763L;

    @NotNull
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate date;
    @NotNull
    @JsonFormat(pattern = "HH:mm")
    private LocalTime commenceTime;
    @NotNull
    private Integer length;
    private PatientDto patient;
    private AppointmentStatus status;
}
