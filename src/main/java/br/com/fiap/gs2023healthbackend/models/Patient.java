package br.com.fiap.gs2023healthbackend.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Table(
    name = "patients"
)
public class Patient extends Person {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "insurance_id")
    private MedicalInsurance insurance;

    @Pattern(regexp = "^[a-zA-Z0-9]*$")
    @Column(name = "insurance_number")
    private String insuranceNumber;

    public Patient(String username, String email, String password, String name, String lastName, String cpf, String rg, MedicalInsurance insurance, String insuranceNumber) {
        super(username, email, password, name, lastName, cpf, rg);
        this.insurance = insurance;
        this.insuranceNumber = insuranceNumber;
    }
}

