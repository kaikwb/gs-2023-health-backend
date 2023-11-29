package br.com.fiap.gs2023healthbackend.payload.response.patient;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PatientSimpleResponse {
    @NotNull
    private Long id;

    @NotBlank
    private String name;

    @NotBlank
    private String lastName;

    private String insurance;

    private String insuranceNumber;
}
