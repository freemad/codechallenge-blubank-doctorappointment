package com.blubank.doctorappointment.services;

import com.blubank.doctorappointment.interfaces.IModelAppointmentConverter;
import com.blubank.doctorappointment.interfaces.IModelPatientConverter;
import com.blubank.doctorappointment.models.dtos.AppointmentDto;
import com.blubank.doctorappointment.models.dtos.PatientDto;
import com.blubank.doctorappointment.models.entities.Appointment;
import com.blubank.doctorappointment.models.entities.Patient;
import com.blubank.doctorappointment.utils.DateUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ModelPatientConverterService implements IModelPatientConverter {

    @Autowired
    ModelMapper modelMapper;

    @Override
    public Patient toEntity(PatientDto dto) {
        if (dto != null) {
            return modelMapper.map(dto, Patient.class);
        }
        return null;
    }

    @Override
    public PatientDto toDto(Patient entity) {
        if (entity != null) {
            return modelMapper.map(entity, PatientDto.class);
        }
        return null;
    }
}
