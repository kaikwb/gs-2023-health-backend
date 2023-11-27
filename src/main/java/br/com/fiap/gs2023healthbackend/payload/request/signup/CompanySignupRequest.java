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
public class CompanySignupRequest extends SignupRequest {
    @NotBlank
    private String cnpj;

    @NotBlank
    private String name;

    @NotBlank
    private String cnes;
}
