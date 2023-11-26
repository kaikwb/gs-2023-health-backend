package br.com.fiap.gs2023healthbackend.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Table(
    name = "medics",
    uniqueConstraints = {
        @UniqueConstraint(columnNames = {"crm", "crm_uf_id"})
    }
)
public class Medic extends Person {
    @NotBlank
    @Column(name = "crm", nullable = false)
    private String crm;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "crm_uf_id", nullable = false)
    private Uf crmUF;

    @Builder.Default
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "medic_specialties",
        joinColumns = @JoinColumn(name = "medic_id"),
        inverseJoinColumns = @JoinColumn(name = "specialty_id"),
        uniqueConstraints = {
            @UniqueConstraint(columnNames = {"medic_id", "specialty_id"}, name = "uk_medic_specialties_medic_specialty")
        }
    )
    private Set<MedialSpeciality> specialties = new HashSet<>();

    @Builder.Default
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "medic_patients",
        joinColumns = @JoinColumn(name = "medic_id"),
        inverseJoinColumns = @JoinColumn(name = "patient_id"),
        uniqueConstraints = {
            @UniqueConstraint(columnNames = {"medic_id", "patient_id"}, name = "uk_medic_patients_medic_patient")
        }
    )
    private Set<Patient> patients = new HashSet<>();

    public Medic(String username, String email, String password, String name, String lastName, String cpf, String rg, String crm, Uf crmUF, Set<MedialSpeciality> specialties, Set<Patient> patients) {
        super(username, email, password, name, lastName, cpf, rg);
        this.crm = crm;
        this.crmUF = crmUF;
        this.specialties = specialties;
        this.patients = patients;
    }
}
