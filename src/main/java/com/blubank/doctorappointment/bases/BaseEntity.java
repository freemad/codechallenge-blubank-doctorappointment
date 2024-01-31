package com.blubank.doctorappointment.bases;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@MappedSuperclass
public abstract class BaseEntity implements Serializable {
    private static final long serialVersionUID = 3414768201421608082L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
}
