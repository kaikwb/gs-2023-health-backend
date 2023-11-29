package br.com.fiap.gs2023healthbackend.controllers.patient;

import br.com.fiap.gs2023healthbackend.models.Medic;
import br.com.fiap.gs2023healthbackend.models.Patient;
import br.com.fiap.gs2023healthbackend.payload.response.patient.PatientResponse;
import br.com.fiap.gs2023healthbackend.payload.response.patient.PatientSimpleResponse;
import br.com.fiap.gs2023healthbackend.repository.MedicRepository;
import br.com.fiap.gs2023healthbackend.repository.MedicalInsuranceRepository;
import br.com.fiap.gs2023healthbackend.repository.PatientRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class PatientBaseController {
    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    PatientRepository patientRepository;

    @Autowired
    MedicRepository medicRepository;

    @Autowired
    MedicalInsuranceRepository medicalInsuranceRepository;

    Boolean isPatient() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        return auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_PATIENT"));
    }

    Boolean isMedic() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        return auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_MEDIC"));
    }

    Patient getPatient() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        return patientRepository.findByUsername(auth.getName()).orElseThrow();
    }

    Medic getMedic() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        return medicRepository.findByUsername(auth.getName()).orElseThrow();
    }

    PatientSimpleResponse getPatientSimpleResponse(Patient patient) {
        return PatientSimpleResponse.builder()
            .id(patient.getId())
            .name(patient.getName())
            .lastName(patient.getLastName())
            .insurance(patient.getInsurance() != null ? patient.getInsurance().getName() : null)
            .insuranceNumber(patient.getInsuranceNumber())
            .build();
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
