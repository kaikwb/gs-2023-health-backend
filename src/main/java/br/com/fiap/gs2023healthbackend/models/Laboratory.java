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
    name = "laboratories"
)
public class Laboratory extends Company {
    @Builder.Default
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "laboratory_exams",
        joinColumns = @JoinColumn(name = "laboratory_id"),
        inverseJoinColumns = @JoinColumn(name = "exam_id"),
        uniqueConstraints = {
            @UniqueConstraint(columnNames = {"laboratory_id", "exam_id"}, name = "uk_laboratory_exams_laboratory_exam")
        }
    )
    private Set<MedicalExam> exams = new HashSet<>();

    public Laboratory(String username, String email, String password, String cnpj, String name, String cnes, Set<MedicalExam> exams) {
        super(username, email, password, cnpj, name, cnes);
        this.exams = exams;
    }
}
