package br.com.fiap.gs2023healthbackend.models;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(
    name = "laboratories"
)
public class Laboratory extends Company {
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "laboratory_exams",
        joinColumns = @JoinColumn(name = "laboratory_id"),
        inverseJoinColumns = @JoinColumn(name = "exam_id"),
        uniqueConstraints = {
            @UniqueConstraint(columnNames = {"laboratory_id", "exam_id"})
        }
    )
    private Set<MedicalExam> exams = new HashSet<>();

    public Laboratory() {
    }

    public Laboratory(String username, String email, String password, String cnpj, String name, String cnes, Set<MedicalExam> exams) {
        super(username, email, password, cnpj, name, cnes);
        this.exams = exams;
    }

    public Set<MedicalExam> getExams() {
        return exams;
    }

    public void setExams(Set<MedicalExam> exams) {
        this.exams = exams;
    }
}
