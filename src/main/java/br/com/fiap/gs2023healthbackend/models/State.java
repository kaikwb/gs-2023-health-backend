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
    name = "states",
    uniqueConstraints = {
        @UniqueConstraint(columnNames = "name", name = "uk_states_name"),
        @UniqueConstraint(columnNames = "uf", name = "uk_states_uf")
    }
)
public class State {
    @Id
    @SequenceGenerator(name = "sq_state", sequenceName = "sq_state", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sq_state")
    private Long id;

    @NotBlank
    @Column(name = "name", nullable = false)
    private String name;

    @NotBlank
    @Column(name = "uf", nullable = false)
    private String uf;
}
