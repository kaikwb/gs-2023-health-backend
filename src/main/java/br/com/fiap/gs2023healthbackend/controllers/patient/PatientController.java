package br.com.fiap.gs2023healthbackend.controllers.patient;

import br.com.fiap.gs2023healthbackend.models.Medic;
import br.com.fiap.gs2023healthbackend.models.Patient;
import br.com.fiap.gs2023healthbackend.payload.response.patient.PatientResponse;
import br.com.fiap.gs2023healthbackend.payload.response.patient.PatientsResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/patients")
public class PatientController extends PatientBaseController {
    @GetMapping
    @PreAuthorize("hasRole('ROLE_PATIENT') or hasRole('ROLE_MEDIC') or hasRole('ROLE_CLINIC')")
    public ResponseEntity<?> getPatients(@PageableDefault(sort = {"name", "lastName"}, size = 10) Pageable pageable) {
        if (Boolean.TRUE.equals(isPatient())) {
            return getPatientInfo();
        } else if (Boolean.TRUE.equals(isMedic())) {
            return getMedicPatientsInfo(pageable);
        }

        return getPatientsInfo(pageable);
    }

    ResponseEntity<PatientsResponse> getPatientsInfo(Pageable pageable) {
        Set<Patient> patients = patientRepository.findAll(pageable).toSet();

        PatientsResponse response = PatientsResponse.builder()
            .patients(patients.stream().map(this::getPatientSimpleResponse).collect(Collectors.toSet()))
            .build();

        return ResponseEntity.ok(response);
    }

    ResponseEntity<PatientsResponse> getMedicPatientsInfo(Pageable pageable) {
        Medic medic = getMedic();

        Set<Patient> patients = medic.getPatients();

        Page<Patient> page = new PageImpl<>(patients.stream().toList(), pageable, patients.size());

        PatientsResponse response = PatientsResponse.builder()
            .patients(page.getContent().stream().map(this::getPatientSimpleResponse).collect(Collectors.toSet()))
            .build();

        return ResponseEntity.ok(response);
    }

    ResponseEntity<PatientResponse> getPatientInfo() {
        Patient patient = getPatient();

        logger.info("Patient [%s] requested information".formatted(patient.getUsername()));

        PatientResponse response = getPatientResponse(patient);

        return ResponseEntity.ok(response);
    }
}
