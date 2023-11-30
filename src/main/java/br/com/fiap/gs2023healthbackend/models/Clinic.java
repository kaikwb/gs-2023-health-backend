package br.com.fiap.gs2023healthbackend.models;

import jakarta.persistence.*;
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
    name = "clinics"
)
public class Clinic extends Company {
    @Builder.Default
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "clinic_medics",
        joinColumns = @JoinColumn(
            name = "clinic_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_clinic_medics_clinic_id")
        ),
        inverseJoinColumns = @JoinColumn(
            name = "medic_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_clinic_medics_medic_id")
        ),
        uniqueConstraints = {
            @UniqueConstraint(columnNames = {"clinic_id", "medic_id"}, name = "uk_clinic_medics_clinic_medic")
        }
    )
    private Set<Medic> medics = new HashSet<>();

    public Clinic(String username, String email, String password, String cnpj, String name, String cnes, Set<Medic> medics) {
        super(username, email, password, cnpj, name, cnes);
        this.medics = medics;
    }
}
