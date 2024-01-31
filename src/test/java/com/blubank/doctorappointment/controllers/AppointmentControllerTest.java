package com.blubank.doctorappointment.controllers;

import com.blubank.doctorappointment.enums.AppointmentStatus;
import com.blubank.doctorappointment.global.Constant;
import com.blubank.doctorappointment.interfaces.IModelAppointmentConverter;
import com.blubank.doctorappointment.models.dtos.AppointmentDto;
import com.blubank.doctorappointment.models.dtos.SchedulingRequest;
import com.blubank.doctorappointment.models.entities.Appointment;
import com.blubank.doctorappointment.models.entities.Patient;
import com.blubank.doctorappointment.repositories.AppointmentRepository;
import com.blubank.doctorappointment.repositories.PatientRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.UUID;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@Slf4j
public class AppointmentControllerTest {

    private final String apiPath = Constant.API_PREFIX + Constant.API_V1 + Constant.API_APPOINTMENTS;

    @Autowired
    AppointmentController appointmentController;
    @Autowired
    AppointmentRepository appointmentRepository;
    @Autowired
    PatientRepository patientRepository;
    @Autowired
    IModelAppointmentConverter modelAppointmentConverter;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Before
    public void init() {
        appointmentRepository.deleteAll();
    }

    @Test
    public void whenStartAndEndTimeLessThanInterval_thenNoResult() throws Exception {
        SchedulingRequest schedulingRequest = SchedulingRequest.builder()
                .date(LocalDate.of(2025, 1, 1))
                .startTime(LocalTime.of(10,0, 0))
                .endTime(LocalTime.of(10, 10, 0))
                .build();
        ResultActions actions = mockMvc.perform(MockMvcRequestBuilders
                .post(apiPath + "/schedule/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(schedulingRequest))
        );
        Assertions.assertEquals(actions.andReturn().getResponse().getStatus(), 200);
        Assertions.assertEquals(actions.andReturn().getResponse().getContentLength(), 0);
    }

    @Test
    public void whenUpdateStatusWithoutPatientProvided_thenNotAcceptableException() throws Exception {
        Appointment appointment = Appointment.builder()
                .status(AppointmentStatus.APPOINTMENT_STATUS_OPEN)
                .commenceTime(new Date())
                .length(30)
                .build();
        appointment = appointmentRepository.save(appointment);
        AppointmentDto appointmentDto = modelAppointmentConverter.toDto(appointment);
        appointmentDto.setStatus(AppointmentStatus.APPOINTMENT_STATUS_APPOINTED);
        ResultActions actions = mockMvc.perform(MockMvcRequestBuilders
                .put(apiPath + "/" + appointment.getId().toString() + "/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(appointmentDto))
        );
        Assertions.assertEquals(actions.andReturn().getResponse().getStatus(), 406);
    }

    @Test
    public void whenAppointedAppointmentBeDeleted_thenLockedException() throws Exception {
        Appointment appointment = Appointment.builder()
                .status(AppointmentStatus.APPOINTMENT_STATUS_APPOINTED)
                .commenceTime(new Date())
                .length(30)
                .build();
        appointment = appointmentRepository.save(appointment);
        ResultActions actions = mockMvc.perform(MockMvcRequestBuilders
                .delete(apiPath + "/" + appointment.getId().toString() + "/")
                .contentType(MediaType.APPLICATION_JSON)
        );
        Assertions.assertEquals(actions.andReturn().getResponse().getStatus(), 406);
    }

    @Test
    public void whenNoAppointmentBeDeleted_thenNotFoundException() throws Exception {
        ResultActions actions = mockMvc.perform(MockMvcRequestBuilders
                .delete(apiPath + "/" + UUID.randomUUID() + "/")
                .contentType(MediaType.APPLICATION_JSON)
        );
        Assertions.assertEquals(actions.andReturn().getResponse().getStatus(), 404);
    }

    @Test
    public void whenNoOpenAppointment_thenNoResult() throws Exception {
        Appointment appointment = Appointment.builder()
                .status(AppointmentStatus.APPOINTMENT_STATUS_APPOINTED)
                .commenceTime(new Date())
                .length(30)
                .build();
        appointmentRepository.save(appointment);
        AppointmentDto appointmentDto = modelAppointmentConverter.toDto(appointment);
        ResultActions actions = mockMvc.perform(MockMvcRequestBuilders
                .get(apiPath + "/" + appointmentDto.getDate() + "/" + AppointmentStatus.APPOINTMENT_STATUS_OPEN + "/")
                .contentType(MediaType.APPLICATION_JSON)
        );
        Assertions.assertEquals(actions.andReturn().getResponse().getStatus(), 200);
        Assertions.assertEquals(actions.andReturn().getResponse().getContentLength(), 0);
    }

    @Test
    public void whenNoAppointmentForPatient_thenNoResult() throws Exception {
        Appointment appointment = Appointment.builder()
                .status(AppointmentStatus.APPOINTMENT_STATUS_APPOINTED)
                .commenceTime(new Date())
                .length(30)
                .build();
        Patient patient = Patient.builder()
                .name("Mrs Akh Injaam")
                .mobileno("+989123456789")
                .build();
        appointmentRepository.save(appointment);
        patientRepository.save(patient);
        ResultActions actions = mockMvc.perform(MockMvcRequestBuilders
                .get(apiPath + "/patients/" + patient.getId() + "/" + AppointmentStatus.APPOINTMENT_STATUS_APPOINTED + "/")
                .contentType(MediaType.APPLICATION_JSON)
        );
        Assertions.assertEquals(actions.andReturn().getResponse().getStatus(), 200);
        Assertions.assertEquals(actions.andReturn().getResponse().getContentLength(), 0);
    }

    @Test
    public void whenMultipleAppointmentsForPatient_thenAllProvided() throws Exception {
        Patient patient = Patient.builder()
                .name("Mrs Akh Injaam")
                .mobileno("+989123456789")
                .build();
        patientRepository.save(patient);
        Appointment appointment1 = Appointment.builder()
                .status(AppointmentStatus.APPOINTMENT_STATUS_APPOINTED)
                .commenceTime(new Date())
                .length(30)
                .patient(patient)
                .build();
        Appointment appointment2 = Appointment.builder()
                .status(AppointmentStatus.APPOINTMENT_STATUS_APPOINTED)
                .commenceTime(new Date())
                .length(30)
                .patient(patient)
                .build();
        appointmentRepository.save(appointment1);
        appointmentRepository.save(appointment2);
        ResultActions actions = mockMvc.perform(MockMvcRequestBuilders
                .get(apiPath + "/patients/" + patient.getId() + "/" + AppointmentStatus.APPOINTMENT_STATUS_APPOINTED + "/")
                .contentType(MediaType.APPLICATION_JSON)
        );
        AppointmentDto[] appointmentDtos = objectMapper.readValue(actions.andReturn().getResponse().getContentAsString(), AppointmentDto[].class);
        Assertions.assertEquals(actions.andReturn().getResponse().getStatus(), 200);
        Assertions.assertEquals(appointmentDtos.length, 2);
    }

}
