package br.com.fiap.gs2023healthbackend.payload.response.patient;

import br.com.fiap.gs2023healthbackend.payload.response.PersonResponse;
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

    private String insuranceNumber;
}
