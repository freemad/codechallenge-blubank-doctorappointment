package com.blubank.doctorappointment.bases;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Id;
import java.io.Serializable;
import java.util.UUID;

@Data
@Getter @Setter
public abstract class BaseDto<T extends BaseEntity> implements Serializable {
    private static final long serialVersionUID = -6332101075955646111L;

    @Id
    private UUID id;
}
