package br.com.fiap.gs2023healthbackend.payload.response.clinic;

import br.com.fiap.gs2023healthbackend.payload.response.medic.MedicSimpleResponse;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Set;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClinicResponse {
    @NotNull
    private Long id;

    @NotBlank
    private String username;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String name;

    @NotBlank
    private String cnpj;

    @NotBlank
    private String cnes;

    private Set<MedicSimpleResponse> medics;
}
