package com.blubank.doctorappointment.services;

import com.blubank.doctorappointment.bases.BaseService;
import com.blubank.doctorappointment.enums.AppointmentStatus;
import com.blubank.doctorappointment.global.CustomProperties;
import com.blubank.doctorappointment.interfaces.IAppointmentService;
import com.blubank.doctorappointment.models.entities.Appointment;
import com.blubank.doctorappointment.models.entities.Patient;
import com.blubank.doctorappointment.repositories.AppointmentRepository;
import com.blubank.doctorappointment.utils.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class AppointmentService
        extends BaseService<Appointment>
        implements IAppointmentService {

    AppointmentRepository appointmentRepository;
    @Autowired
    CustomProperties customProperties;

    @Autowired
    public AppointmentService(AppointmentRepository appointmentRepository) {
        super(appointmentRepository);
        this.appointmentRepository = appointmentRepository;
    }

    @Override
    @Transactional
    public List<Appointment> findByCommenceTimeRange(LocalDate localDate, LocalTime localStartTime, LocalTime localEndTime) {
        Date start = DateUtil.toDate(localDate, localStartTime);
        Date end = DateUtil.toDate(localDate, localEndTime);
        return appointmentRepository.findByStartTimeBetween(start, end);
    }

    @Override
    @Transactional
    public List<Appointment> createByTimeRange(LocalDate localDate, LocalTime localStartTime, LocalTime localEndTime) {
        Integer length = customProperties.getAppointmentInterval();
        Date start = DateUtil.toDate(localDate, localStartTime);
        Date end = DateUtil.toDate(localDate, localEndTime);
        List<Appointment> appointments = new ArrayList<>();
        while (DateUtil.toDate(localDate, DateUtil.toLocalTime(start).plusMinutes(length)).before(end)) {
            Appointment appointment = Appointment.builder()
                    .length(length)
                    .commenceTime(start)
                    .status(AppointmentStatus.APPOINTMENT_STATUS_OPEN)
                    .build();
            appointments.add(appointment);
            start = DateUtil.toDate(localDate, DateUtil.toLocalTime(start).plusMinutes(length));
        }
        this.appointmentRepository.saveAll(appointments);
        return appointments;
    }

    @Override
    @Transactional
    public List<Appointment> updateBulkStatus(List<Appointment> appointments, AppointmentStatus status) {
        appointments.forEach(appointment -> appointment.setStatus(status));
        appointmentRepository.saveAll(appointments);
        return appointments;
    }

    @Override
    @Transactional
    public List<Appointment> updateBulkPatient(List<Appointment> appointments, Patient patient) {
        appointments.forEach(appointment -> {
            appointment.setPatient(patient);
            appointmentRepository.save(appointment);});
        return appointments;
    }

    @Override
    @Transactional
    public List<Appointment> findByDateAndSStatus(LocalDate date, AppointmentStatus status) {
        Date startingTimestamp = DateUtil.toDate(date, LocalTime.of(0, 0));
        Date endingTimestamp = DateUtil.toDate(date, LocalTime.of(23, 59));
        return appointmentRepository.findByStartTimeBetweenAndStatus(startingTimestamp, endingTimestamp, status);
    }

    @Override
    @Transactional
    public List<Appointment> findByPatientAndSStatus(Patient patient, AppointmentStatus status) {
        return appointmentRepository.findByPatientAndStatus(patient, status);
    }
}
