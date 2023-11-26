package br.com.fiap.gs2023healthbackend.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(
    name = "medical_insurances",
    uniqueConstraints = {
        @UniqueConstraint(columnNames = "name", name = "uk_medical_insurances_name")
    }
)
public class MedicalInsurance {
    @Id
    @SequenceGenerator(name = "sq_medical_insurances", sequenceName = "sq_medical_insurances", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sq_medical_insurances")
    private Long id;

    @NotBlank
    @Column(name = "name", nullable = false)
    private String name;
}
