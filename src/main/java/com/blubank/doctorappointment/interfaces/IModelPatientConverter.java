package com.blubank.doctorappointment.interfaces;

import com.blubank.doctorappointment.bases.IBaseModelConverter;
import com.blubank.doctorappointment.models.dtos.PatientDto;
import com.blubank.doctorappointment.models.entities.Patient;

public interface IModelPatientConverter extends IBaseModelConverter<Patient, PatientDto> {
}
