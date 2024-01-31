package com.blubank.doctorappointment.services;

import com.blubank.doctorappointment.interfaces.IModelAppointmentConverter;
import com.blubank.doctorappointment.models.dtos.AppointmentDto;
import com.blubank.doctorappointment.models.entities.Appointment;
import com.blubank.doctorappointment.utils.DateUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ModelAppointmentConverterService implements IModelAppointmentConverter {

    @Autowired
    ModelMapper modelMapper;

    @Override
    public Appointment toEntity(AppointmentDto dto) {
        if (dto != null) {
            Appointment appointment = modelMapper.map(dto, Appointment.class);
            appointment.setCommenceTime(DateUtil.toDate(dto.getDate(), dto.getCommenceTime()));
            return appointment;
        }
        return null;
    }

    @Override
    public AppointmentDto toDto(Appointment entity) {
        if (entity != null) {
            AppointmentDto appointmentDto = modelMapper.map(entity, AppointmentDto.class);
            appointmentDto.setDate(DateUtil.toLocalDate(entity.getCommenceTime()));
            appointmentDto.setCommenceTime(DateUtil.toLocalTime(entity.getCommenceTime()));
            return appointmentDto;
        }
        return null;
    }
}
