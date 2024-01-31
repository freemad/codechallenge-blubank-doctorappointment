package com.blubank.doctorappointment.models.dtos;

import com.blubank.doctorappointment.bases.BaseDto;
import com.blubank.doctorappointment.models.entities.Patient;
import lombok.*;

import javax.persistence.Id;
import javax.validation.constraints.*;
import java.util.UUID;

@Data
@Builder
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class PatientDto extends BaseDto<Patient> {
    private static final long serialVersionUID = 257319357405641518L;

    @NotBlank
    private String name;
    @NotBlank
    @Pattern(regexp = "^((\\+|00)98|0)9[0-3]\\d{8}$")
    private String mobileno;
}
