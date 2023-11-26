package br.com.fiap.gs2023healthbackend.repository;

import br.com.fiap.gs2023healthbackend.models.Patient;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientRepository extends PersonRepository<Patient> {

}
