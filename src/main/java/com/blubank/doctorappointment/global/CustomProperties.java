package com.blubank.doctorappointment.global;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "com.blubank.doctorappointment")
public class CustomProperties {

    @Value("com.blubank.doctorappointment.doctor-name")
    private String doctorName;
    @Value("com.blubank.doctorappointment.appointment-interval")
    private String appointmentInterval;

    public Integer getAppointmentInterval() {
        return Integer.parseInt(appointmentInterval);
    }
}
