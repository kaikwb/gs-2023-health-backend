package br.com.fiap.gs2023healthbackend.models;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Nationalized;

import java.time.ZonedDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(
    name = "medical_appointments"
)
public class MedicalAppointment {
    @Id
    @SequenceGenerator(name = "sq_medical_appointments", sequenceName = "sq_medical_appointments", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sq_medical_appointments")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "patient_id", referencedColumnName = "id", nullable = false)
    private Patient patient;

    @ManyToOne
    @JoinColumn(name = "medic_id", referencedColumnName = "id", nullable = false)
    private Medic medic;

    @Column(name = "appointment_date", nullable = false)
    private ZonedDateTime date;

    @Lob
    @Nationalized
    @Column(nullable = false)
    private String description;
}
