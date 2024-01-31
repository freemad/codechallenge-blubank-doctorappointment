package com.blubank.doctorappointment.bases;

import com.blubank.doctorappointment.models.dtos.AppointmentDto;
import com.blubank.doctorappointment.models.entities.Appointment;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

public abstract class BaseController<T extends BaseEntity, D extends BaseDto<T>> {

    protected boolean checkIdEquals(UUID id, BaseDto<T> dto) {
        return Objects.equals(id, dto.getId());
    }

    @NotNull
    protected List<D> toDtoList(List<T> entities, IBaseModelConverter<T, D> modelConverter) {
        return entities.stream()
                .map(modelConverter::toDto)
                .collect(Collectors.toList());
    }

}
