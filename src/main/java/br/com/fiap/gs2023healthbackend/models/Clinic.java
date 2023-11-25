package br.com.fiap.gs2023healthbackend.models;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(
    name = "clinics"
)
public class Clinic extends Company {
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "clinic_medics",
        joinColumns = @JoinColumn(name = "clinic_id"),
        inverseJoinColumns = @JoinColumn(name = "medic_id"),
        uniqueConstraints = {
            @UniqueConstraint(columnNames = {"clinic_id", "medic_id"})
        }
    )
    private Set<Medic> medics = new HashSet<>();

    public Clinic() {
    }

    public Clinic(String username, String email, String password, String cnpj, String name, String cnes, Set<Medic> medics) {
        super(username, email, password, cnpj, name, cnes);
        this.medics = medics;
    }

    public Set<Medic> getMedics() {
        return medics;
    }

    public void setMedics(Set<Medic> medics) {
        this.medics = medics;
    }
}
