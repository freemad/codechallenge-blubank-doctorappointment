package com.blubank.doctorappointment.controllers;

import com.blubank.doctorappointment.bases.BaseController;
import com.blubank.doctorappointment.bases.BaseDto;
import com.blubank.doctorappointment.bases.IBaseModelConverter;
import com.blubank.doctorappointment.exceptions.BusinessException;
import com.blubank.doctorappointment.exceptions.ErrorCode;
import com.blubank.doctorappointment.interfaces.IModelPatientConverter;
import com.blubank.doctorappointment.models.dtos.PatientDto;
import com.blubank.doctorappointment.models.entities.Patient;
import com.blubank.doctorappointment.global.Constant;
import com.blubank.doctorappointment.interfaces.IPatientService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.webjars.NotFoundException;

import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping(Constant.API_PREFIX + Constant.API_V1 + Constant.API_PATIENTS)
@Tag(name = "Patient Controller", description = "the Patient API for managing the Patient entity")
public class PatientController extends BaseController<Patient, PatientDto> {

    ResourceBundle messages = ResourceBundle.getBundle("ValidationMessages");

    @Autowired
    IPatientService patientService;
    @Autowired
    IBaseModelConverter<Patient, PatientDto> modelPatientConverter;

    @GetMapping("/{id}/")
    @ResponseBody
    public PatientDto getById(@PathVariable UUID id) throws BusinessException {
        return modelPatientConverter.toDto(patientService.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_EXCEPTION, messages.getString("validation.notFound"))));
    }

    @GetMapping("/mobile/{mobileno}/")
    @ResponseBody
    public PatientDto getByMobileno(@PathVariable String mobileno) throws BusinessException {
        return modelPatientConverter.toDto(patientService.findByMobileno(mobileno)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_EXCEPTION, messages.getString("validation.notFound"))));
    }

    @GetMapping("/")
    @ResponseBody
    public List<PatientDto> getAll() {
        List<Patient> patients = patientService.findAll();
        return toDtoList(patients, modelPatientConverter);
    }

    @PostMapping("/")
    @ResponseBody
    public PatientDto create(@RequestBody @Valid PatientDto patientDto) {
        return modelPatientConverter.toDto(
                patientService.save(modelPatientConverter.toEntity(patientDto)));
    }

    @PutMapping("/{id}/")
    @ResponseBody
    public PatientDto update(@PathVariable UUID id, @RequestBody @Valid PatientDto patientDto) throws BusinessException {
        if (!checkIdEquals(id, patientDto)) {
            throw new BusinessException(ErrorCode.BAD_PARAM_EXCEPTION, messages.getString("validation.idsMatch"));
        }
        return modelPatientConverter.toDto(
                patientService.save(modelPatientConverter.toEntity(patientDto)));
    }

    @DeleteMapping("/{id}/")
    @ResponseBody
    public PatientDto delete(@PathVariable UUID id) throws BusinessException {
        try {
            return modelPatientConverter.toDto(patientService.delete(id));
        } catch (NotFoundException exception) {
            throw new BusinessException(ErrorCode.NOT_FOUND_EXCEPTION, messages.getString("validation.notFound"));
        }
    }
}
