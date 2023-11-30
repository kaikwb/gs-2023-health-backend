package br.com.fiap.gs2023healthbackend.repository;

import br.com.fiap.gs2023healthbackend.models.MedicalAppointment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface MedicalAppointmentRepository extends JpaRepository<MedicalAppointment, Long> {
    Set<MedicalAppointment> findAllByPatientId(Long patientId);

    Page<MedicalAppointment> findAllByPatientId(Long patientId, Pageable pageable);

    Set<MedicalAppointment> findAllByMedicId(Long medicId);

    Page<MedicalAppointment> findAllByMedicId(Long medicId, Pageable pageable);
}
