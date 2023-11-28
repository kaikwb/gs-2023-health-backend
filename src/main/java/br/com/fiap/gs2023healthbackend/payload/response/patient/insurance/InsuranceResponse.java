package br.com.fiap.gs2023healthbackend.payload.response.patient.insurance;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InsuranceResponse {
    private String insurance;

    private String insuranceNumber;
}
