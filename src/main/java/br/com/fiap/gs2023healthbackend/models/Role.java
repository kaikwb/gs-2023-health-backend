package br.com.fiap.gs2023healthbackend.models;

import br.com.fiap.gs2023healthbackend.enums.ERole;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@DiscriminatorColumn(name = "user_type", discriminatorType = DiscriminatorType.STRING)
@Table(name = "roles",
    uniqueConstraints = {
        @UniqueConstraint(columnNames = "name", name = "uk_roles_name")
    }
)
public class Role {
    @Id
    @SequenceGenerator(name = "sq_roles", sequenceName = "sq_roles", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sq_roles")
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(length = 20, nullable = false)
    private ERole name;

    public Role(ERole name) {
        this.name = name;
    }
}