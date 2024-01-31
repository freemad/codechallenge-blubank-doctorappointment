package com.blubank.doctorappointment.interfaces;

import com.blubank.doctorappointment.bases.IBaseService;
import com.blubank.doctorappointment.models.dtos.PatientDto;
import com.blubank.doctorappointment.models.entities.Patient;

import java.util.Optional;

public interface IPatientService extends IBaseService<Patient> {
    public Optional<Patient> findByMobileno(String mobileno);
}
