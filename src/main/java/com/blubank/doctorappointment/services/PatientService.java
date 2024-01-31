package com.blubank.doctorappointment.services;

import com.blubank.doctorappointment.bases.BaseService;
import com.blubank.doctorappointment.models.entities.Patient;
import com.blubank.doctorappointment.interfaces.IPatientService;
import com.blubank.doctorappointment.repositories.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class PatientService
        extends BaseService<Patient>
        implements IPatientService {

    PatientRepository patientRepository;

    @Autowired
    public PatientService(PatientRepository patientRepository) {
        super(patientRepository);
        this.patientRepository = patientRepository;
    }

    @Override
    @Transactional
    public Optional<Patient> findByMobileno(String mobileno) {
        return patientRepository.findByMobileno(mobileno);
    }
}
