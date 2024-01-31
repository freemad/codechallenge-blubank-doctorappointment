package com.blubank.doctorappointment.enums;

import lombok.Getter;

@Getter
public enum AppointmentStatus {
    APPOINTMENT_STATUS_OPEN((short) 0),
    APPOINTMENT_STATUS_APPOINTING((short) 1),
    APPOINTMENT_STATUS_APPOINTED((short) 2);

    private final short status;

    AppointmentStatus(short status) {
        this.status = status;
    }
}
