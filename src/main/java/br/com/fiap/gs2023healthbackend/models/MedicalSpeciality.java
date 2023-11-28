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
    name = "medical_specialities",
    uniqueConstraints = {
        @UniqueConstraint(columnNames = "name", name = "uk_medical_specialities_name")
    }
)
public class MedicalSpeciality {
    @Id
    @SequenceGenerator(name = "sq_medical_specialities", sequenceName = "sq_medical_specialities", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sq_medical_specialities")
    private Long id;

    @NotBlank
    @Column(name = "name", nullable = false)
    private String name;
}
