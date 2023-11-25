package br.com.fiap.gs2023healthbackend.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
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

    public Uf() {
    }

    public Uf(Long id, String name) {
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
