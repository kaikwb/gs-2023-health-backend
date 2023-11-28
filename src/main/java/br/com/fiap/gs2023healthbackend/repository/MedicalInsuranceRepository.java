package br.com.fiap.gs2023healthbackend.repository;

import br.com.fiap.gs2023healthbackend.models.MedicalInsurance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MedicalInsuranceRepository extends JpaRepository<MedicalInsurance, Long> {
    Optional<MedicalInsurance> findByName(String name);
}
