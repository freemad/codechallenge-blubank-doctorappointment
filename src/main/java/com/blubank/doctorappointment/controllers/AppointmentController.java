package com.blubank.doctorappointment.controllers;

import com.blubank.doctorappointment.bases.BaseController;
import com.blubank.doctorappointment.bases.IBaseModelConverter;
import com.blubank.doctorappointment.enums.AppointmentStatus;
import com.blubank.doctorappointment.exceptions.BusinessException;
import com.blubank.doctorappointment.exceptions.ErrorCode;
import com.blubank.doctorappointment.global.Constant;
import com.blubank.doctorappointment.interfaces.IAppointmentService;
import com.blubank.doctorappointment.interfaces.IPatientService;
import com.blubank.doctorappointment.models.dtos.AppointmentDto;
import com.blubank.doctorappointment.models.dtos.PatientDto;
import com.blubank.doctorappointment.models.dtos.SchedulingRequest;
import com.blubank.doctorappointment.models.entities.Appointment;
import com.blubank.doctorappointment.models.entities.Patient;
import com.blubank.doctorappointment.utils.LocalTimeValidationUtil;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.MessageFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.ResourceBundle;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping(Constant.API_PREFIX + Constant.API_V1 + Constant.API_APPOINTMENTS)
@Tag(name = "Appointment Controller", description = "the Appointment API for managing the Appointment entity")
public class AppointmentController extends BaseController<Appointment, AppointmentDto> {

    ResourceBundle messages = ResourceBundle.getBundle("ValidationMessages");

    @Autowired
    IAppointmentService appointmentService;
    @Autowired
    IPatientService patientService;
    @Autowired
    IBaseModelConverter<Appointment, AppointmentDto> modelAppointmentConverter;

    @GetMapping("/{id}/")
    @ResponseBody
    public AppointmentDto getById(@PathVariable UUID id) throws BusinessException {
        return modelAppointmentConverter.toDto(appointmentService.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_EXCEPTION, messages.getString("validation.notFound"))));
    }

    @GetMapping("/")
    @ResponseBody
    public List<AppointmentDto> getAll() {
        List<Appointment> appointments = appointmentService.findAll();
        return toDtoList(appointments, modelAppointmentConverter);
    }

    @PostMapping("/")
    @ResponseBody
    public AppointmentDto create(@RequestBody @Valid AppointmentDto appointmentDto) {
        return modelAppointmentConverter.toDto(
                appointmentService.save(modelAppointmentConverter.toEntity(appointmentDto)));
    }

    @PutMapping("/{id}/")
    @ResponseBody
    public AppointmentDto update(@PathVariable UUID id, @RequestBody @Valid AppointmentDto appointmentDto) throws BusinessException {
        if (!checkIdEquals(id, appointmentDto)) {
            throw new BusinessException(ErrorCode.BAD_PARAM_EXCEPTION, messages.getString("validation.idsMatch"));
        }
        if (!appointmentDto.getStatus().equals(AppointmentStatus.APPOINTMENT_STATUS_OPEN)
                && isPatientProper(appointmentDto.getPatient())
        ) {
            throw new BusinessException(
                    ErrorCode.BAD_PARAM_EXCEPTION,
                    MessageFormat.format(messages.getString("validation.notProvided"), "Patient"));
        }
        Appointment appointment = appointmentService.save(modelAppointmentConverter.toEntity(appointmentDto));
        return modelAppointmentConverter.toDto(appointment);
    }

    @DeleteMapping("/{id}/")
    public AppointmentDto delete(@PathVariable UUID id) throws BusinessException {
        Appointment appointment = appointmentService.findById(id).orElseThrow(
                () -> new BusinessException(ErrorCode.NOT_FOUND_EXCEPTION, messages.getString("validation.notFound")));
        if (!appointment.getStatus().equals(AppointmentStatus.APPOINTMENT_STATUS_OPEN)) {
            throw new BusinessException(ErrorCode.LOCKED_EXCEPTION, messages.getString("validation.locked"));
        }
        return modelAppointmentConverter.toDto(appointmentService.delete(id));
    }

    @GetMapping("/{date}/{start}/{end}/")
    @ResponseBody
    public List<AppointmentDto> getByCommenceTimeRange(
            @PathVariable @Valid String date,
            @PathVariable @Valid String start,
            @PathVariable @Valid String end) {
        List<Appointment> appointments = appointmentService.findByCommenceTimeRange(
                LocalDate.parse(date), LocalTime.parse(start), LocalTime.parse(end));
        return toDtoList(appointments, modelAppointmentConverter);
    }

    @GetMapping("/{date}/{status}/")
    @ResponseBody
    public List<AppointmentDto> getByDateAndStatus(
            @PathVariable @Valid String date,
            @PathVariable @Valid AppointmentStatus status) {
        List<Appointment> appointments = appointmentService.findByDateAndSStatus(LocalDate.parse(date), status);
        return toDtoList(appointments, modelAppointmentConverter);
    }

    @PostMapping("/schedule/")
    @ResponseBody
    public List<AppointmentDto> createSchedule(@RequestBody @Valid SchedulingRequest schedulingRequest) throws BusinessException {
        if (!LocalTimeValidationUtil.timeOrderIsValid(schedulingRequest.getStartTime(), schedulingRequest.getEndTime())) {
            throw new BusinessException(ErrorCode.BAD_PARAM_EXCEPTION, messages.getString("validation.startAndEndTimeInOrder"));
        }
        List<Appointment> appointments = appointmentService.createByTimeRange(
                schedulingRequest.getDate(),
                schedulingRequest.getStartTime(),
                schedulingRequest.getEndTime());
        log.debug("appoint: " + appointments);
        return toDtoList(appointments, modelAppointmentConverter);
    }

    @GetMapping("/patients/{patientId}/{status}")
    @ResponseBody
    public List<AppointmentDto> getAppointmentsByPatient(
            @PathVariable @Valid UUID patientId,
            @PathVariable @Valid AppointmentStatus status) throws BusinessException {
        Patient patient = patientService.findById(patientId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_EXCEPTION, messages.getString("validation.notFound")));
        List<Appointment> appointments = appointmentService.findByPatientAndSStatus(patient, status);
        return toDtoList(appointments, modelAppointmentConverter);
    }

    @PutMapping("/status/{status}/")
    @ResponseBody
    public List<AppointmentDto> updateBulkStatus(
            @PathVariable AppointmentStatus status,
            @RequestBody @Valid List<AppointmentDto> appointmentDtos) {
        List<Appointment> appointments = appointmentDtos.stream()
                .map(modelAppointmentConverter::toEntity)
                .collect(Collectors.toList());
        List<Appointment> updatedAppointments = appointmentService.updateBulkStatus(appointments, status);
        return toDtoList(appointments, modelAppointmentConverter);
    }

    private boolean isPatientProper(PatientDto patientDto) {
        return patientDto == null
                || patientDto.getName().isEmpty()
                || patientDto.getMobileno().isEmpty();
    }
}
