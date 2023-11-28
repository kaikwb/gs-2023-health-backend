package br.com.fiap.gs2023healthbackend.payload.response.patient.insurance;

import br.com.fiap.gs2023healthbackend.payload.response.PersonResponse;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class PatientResponse extends PersonResponse {
    private String insurance;

    @NotBlank
    private String insuranceNumber;
}
