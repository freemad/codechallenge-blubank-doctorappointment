package com.blubank.doctorappointment.controllers;

import com.blubank.doctorappointment.global.Constant;
import com.blubank.doctorappointment.models.dtos.PatientDto;
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

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@Slf4j
public class PatientControllerTest {

    private final String apiPath = Constant.API_PREFIX + Constant.API_V1 + Constant.API_PATIENTS;

    @Autowired
    PatientController patientController;
    @Autowired
    PatientRepository patientRepository;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Before
    public void init() {
        patientRepository.deleteAll();
    }

    @Test
    public void whenMobilenoNotValid_thenBadParamException() throws Exception {
        PatientDto patientWithInvalidMobileno = PatientDto.builder()
                .mobileno("+1234567")
                .name("Mrs. Akh Injaam")
                .build();
        ResultActions actions = mockMvc.perform(MockMvcRequestBuilders
                .post(apiPath + "/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(patientWithInvalidMobileno))
        );
        Assertions.assertEquals(actions.andReturn().getResponse().getStatus(), 400);
    }

    @Test
    public void whenMobilenoNoNotProvided_thenBadParamException() throws Exception {
        PatientDto patientWithInvalidMobileno = PatientDto.builder()
                .name("Mrs. Akh Injaam")
                .build();
        ResultActions actions = mockMvc.perform(MockMvcRequestBuilders
                .post(apiPath + "/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(patientWithInvalidMobileno))
        );
        Assertions.assertEquals(actions.andReturn().getResponse().getStatus(), 400);
    }

    @Test
    public void whenNameNotNotProvided_thenBadParamException() throws Exception {
        PatientDto patientWithInvalidMobileno = PatientDto.builder()
                .mobileno("+989123456789")
                .build();
        ResultActions actions = mockMvc.perform(MockMvcRequestBuilders
                .post(apiPath + "/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(patientWithInvalidMobileno))
        );
        Assertions.assertEquals(actions.andReturn().getResponse().getStatus(), 400);
    }
}
