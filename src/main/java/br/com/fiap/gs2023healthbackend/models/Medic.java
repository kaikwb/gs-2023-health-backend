package br.com.fiap.gs2023healthbackend.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.util.HashSet;
import java.util.Set;

@Entity
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

    @ManyToOne
    @JoinColumn(name = "crm_uf_id", nullable = false)
    private Uf crmUF;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "medic_specialties",
        joinColumns = @JoinColumn(name = "medic_id"),
        inverseJoinColumns = @JoinColumn(name = "specialty_id"),
        uniqueConstraints = {
            @UniqueConstraint(columnNames = {"medic_id", "specialty_id"})
        }
    )
    private Set<MedialSpeciality> specialties = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "medic_patients",
        joinColumns = @JoinColumn(name = "medic_id"),
        inverseJoinColumns = @JoinColumn(name = "patient_id"),
        uniqueConstraints = {
            @UniqueConstraint(columnNames = {"medic_id", "patient_id"})
        }
    )
    private Set<Patient> patients = new HashSet<>();

    public Medic() {
    }

    public Medic(String username, String email, String password, String name, String lastName, String cpf, String rg, String crm, Uf crmUF, Set<MedialSpeciality> specialties, Set<Patient> patients) {
        super(username, email, password, name, lastName, cpf, rg);
        this.crm = crm;
        this.crmUF = crmUF;
        this.specialties = specialties;
        this.patients = patients;
    }

    public String getCrm() {
        return crm;
    }

    public void setCrm(String crm) {
        this.crm = crm;
    }

    public Uf getCrmUF() {
        return crmUF;
    }

    public void setCrmUF(Uf crmUF) {
        this.crmUF = crmUF;
    }

    public Set<MedialSpeciality> getSpecialties() {
        return specialties;
    }

    public void setSpecialties(Set<MedialSpeciality> specialties) {
        this.specialties = specialties;
    }

    public Set<Patient> getPatients() {
        return patients;
    }

    public void setPatients(Set<Patient> patients) {
        this.patients = patients;
    }
}
