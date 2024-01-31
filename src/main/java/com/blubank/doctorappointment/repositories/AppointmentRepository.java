package com.blubank.doctorappointment.repositories;

import com.blubank.doctorappointment.models.entities.Appointment;
import com.blubank.doctorappointment.models.entities.Patient;
import com.blubank.doctorappointment.enums.AppointmentStatus;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, UUID> {

    @Query("SELECT a from Appointment a " +
            "where a.status = :status " +
            "and (a.commenceTime > :startingTimestamp and  a.commenceTime <= :endingTimestamp)")
    List<Appointment> findByStartTimeBetweenAndStatus(
            @Param("startingTimestamp") Date startingTimestamp,
            @Param("endingTimestamp") Date endingTimestamp,
            @Param("status") AppointmentStatus status);
    @Query("SELECT a from Appointment a " +
            "where  (a.commenceTime > :startingTimestamp and  a.commenceTime <= :endingTimestamp)")
    List<Appointment> findByStartTimeBetween(
            @Param("startingTimestamp") Date startingTimestamp,
            @Param("endingTimestamp") Date endingTimestamp);
    List<Appointment> findByPatientAndStatus(Patient patient, AppointmentStatus status);
}
