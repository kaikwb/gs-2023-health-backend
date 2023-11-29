package br.com.fiap.gs2023healthbackend.payload.response.medic;

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
public class MedicSimpleResponse {
    @NotNull
    private Long id;

    @NotBlank
    private String name;

    @NotBlank
    private String lastName;

    @NotBlank
    private String crm;

    @NotBlank
    private String crmUf;

    private Set<String> specialties;
}
