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
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@Inheritance(strategy = InheritanceType.JOINED)
@Table(
    name = "persons",
    uniqueConstraints = {
        @UniqueConstraint(columnNames = "cpf", name = "uk_persons_cpf"),
        @UniqueConstraint(columnNames = "rg", name = "uk_persons_rg")
    }
)
public class Person extends User {
    @NotBlank
    @Column(name = "name", nullable = false)
    private String name;

    @NotBlank
    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "cpf")
    private String cpf;

    @NotBlank
    @Column(name = "rg", nullable = false)
    private String rg;

    public Person(String username, String email, String password, String name, String lastName, String cpf, String rg) {
        super(username, email, password);
        this.name = name;
        this.lastName = lastName;
        this.cpf = cpf;
        this.rg = rg;
    }
}
