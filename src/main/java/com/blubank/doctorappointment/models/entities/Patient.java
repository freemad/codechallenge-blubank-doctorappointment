package com.blubank.doctorappointment.models.entities;

import com.blubank.doctorappointment.bases.BaseEntity;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Builder
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Entity
@Table(name = "PATIENTS")
public class Patient extends BaseEntity {
    private static final long serialVersionUID = -2657649921779468069L;

    @Column
    private String name;
    @Column(unique = true)
    private String mobileno;
}
