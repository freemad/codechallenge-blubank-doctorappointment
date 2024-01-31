package com.blubank.doctorappointment.interfaces;

import com.blubank.doctorappointment.bases.IBaseModelConverter;
import com.blubank.doctorappointment.models.dtos.AppointmentDto;
import com.blubank.doctorappointment.models.entities.Appointment;

public interface IModelAppointmentConverter extends IBaseModelConverter<Appointment, AppointmentDto> {
}
