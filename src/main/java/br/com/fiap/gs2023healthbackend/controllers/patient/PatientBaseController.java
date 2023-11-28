package br.com.fiap.gs2023healthbackend.controllers.patient;

import br.com.fiap.gs2023healthbackend.models.Patient;
import br.com.fiap.gs2023healthbackend.payload.response.patient.insurance.PatientResponse;
import br.com.fiap.gs2023healthbackend.repository.MedicalInsuranceRepository;
import br.com.fiap.gs2023healthbackend.repository.PatientRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class PatientBaseController {
    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    PatientRepository patientRepository;

    @Autowired
    MedicalInsuranceRepository medicalInsuranceRepository;

    Patient getPatient() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        return patientRepository.findByUsername(auth.getName()).orElseThrow();
    }

    PatientResponse getPatientResponse(Patient patient) {
        return PatientResponse.builder()
            .id(patient.getId())
            .username(patient.getUsername())
            .email(patient.getEmail())
            .name(patient.getName())
            .lastName(patient.getLastName())
            .cpf(patient.getCpf())
            .rg(patient.getRg())
            .insurance(patient.getInsurance() != null ? patient.getInsurance().getName() : null)
            .insuranceNumber(patient.getInsuranceNumber())
            .build();
    }
}
