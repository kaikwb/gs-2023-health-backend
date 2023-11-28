package br.com.fiap.gs2023healthbackend.repository;

import br.com.fiap.gs2023healthbackend.models.MedicalSpeciality;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface MedicalSpecialityRepository extends JpaRepository<MedicalSpeciality, Long> {
    Set<MedicalSpeciality> findAllByNameIn(Set<String> names);
}
