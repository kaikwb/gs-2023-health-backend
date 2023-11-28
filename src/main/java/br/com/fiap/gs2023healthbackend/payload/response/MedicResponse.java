package br.com.fiap.gs2023healthbackend.payload.response;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class MedicResponse extends PersonResponse {
    @NotBlank
    private String crm;

    @NotBlank
    private String crmUf;

    @NotNull
    private List<String> specialties;
}
