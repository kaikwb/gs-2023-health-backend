package br.com.fiap.gs2023healthbackend.payload.response;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class PersonResponse extends UserResponse {
    @NotBlank
    private String name;

    @NotBlank
    private String lastName;

    private String cpf;

    @NotBlank
    private String rg;
}
