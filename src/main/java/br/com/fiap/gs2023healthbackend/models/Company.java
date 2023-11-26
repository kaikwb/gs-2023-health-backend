package br.com.fiap.gs2023healthbackend.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Inheritance(strategy = InheritanceType.JOINED)
@Table(
    name = "companies",
    uniqueConstraints = {
        @UniqueConstraint(columnNames = "cnpj", name = "uk_companies_cnpj"),
        @UniqueConstraint(columnNames = "cnes", name = "uk_companies_cnes")
    }
)
public class Company extends User {
    @NotBlank
    @Column(name = "cnpj", nullable = false)
    private String cnpj;

    @NotBlank
    @Column(name = "name", nullable = false)
    private String name;

    @NotBlank
    @Column(name = "cnes", nullable = false)
    private String cnes;

    public Company(String username, String email, String password, String cnpj, String name, String cnes) {
        super(username, email, password);
        this.cnpj = cnpj;
        this.name = name;
        this.cnes = cnes;
    }
}
