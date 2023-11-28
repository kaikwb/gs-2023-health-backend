package br.com.fiap.gs2023healthbackend.payload.request.patient.insurance;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SetInsuranceRequest {
    @NotBlank
    private String insurance;

    @NotBlank
    private String insuranceNumber;
}
