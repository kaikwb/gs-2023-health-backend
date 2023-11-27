package br.com.fiap.gs2023healthbackend.payload.request.signup;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class MedicSignupRequest extends PersonSignupRequest {
    @NotBlank
    private String crm;

    @NotBlank
    private String crmUf;
}
