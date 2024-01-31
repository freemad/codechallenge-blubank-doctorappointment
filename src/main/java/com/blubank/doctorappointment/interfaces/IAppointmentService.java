package com.blubank.doctorappointment.interfaces;

import com.blubank.doctorappointment.bases.IBaseService;
import com.blubank.doctorappointment.enums.AppointmentStatus;
import com.blubank.doctorappointment.models.entities.Appointment;
import com.blubank.doctorappointment.models.entities.Patient;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface IAppointmentService extends IBaseService<Appointment> {
    public List<Appointment> findByCommenceTimeRange(LocalDate localDate, LocalTime localStartTime, LocalTime localEndTime);
    public List<Appointment> createByTimeRange(LocalDate localDate, LocalTime localStartTime, LocalTime localEndTime);
    public List<Appointment> updateBulkStatus(List<Appointment> appointments, AppointmentStatus status);
    public List<Appointment> updateBulkPatient(List<Appointment> appointments, Patient patient);
    public List<Appointment> findByDateAndSStatus(LocalDate date, AppointmentStatus status);
    public List<Appointment> findByPatientAndSStatus(Patient patient, AppointmentStatus status);
}
