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
    name = "ufs",
    uniqueConstraints = {
        @UniqueConstraint(columnNames = "name", name = "uk_ufs_name")
    }
)
public class Uf {
    @Id
    @SequenceGenerator(name = "sq_ufs", sequenceName = "sq_ufs", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sq_ufs")
    private Long id;

    @NotBlank
    @Column(name = "name", nullable = false)
    private String name;
}
