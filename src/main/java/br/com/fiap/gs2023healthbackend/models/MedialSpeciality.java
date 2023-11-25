package br.com.fiap.gs2023healthbackend.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(
    name = "medical_specialities",
    uniqueConstraints = {
        @UniqueConstraint(columnNames = "name", name = "uk_medical_specialities_name")
    }
)
public class MedialSpeciality {
    @Id
    @SequenceGenerator(name = "sq_medical_specialities", sequenceName = "sq_medical_specialities", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sq_medical_specialities")
    private Long id;

    @NotBlank
    @Column(name = "name", nullable = false)
    private String name;

    public MedialSpeciality() {
    }

    public MedialSpeciality(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
