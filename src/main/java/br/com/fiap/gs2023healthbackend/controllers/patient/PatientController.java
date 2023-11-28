package br.com.fiap.gs2023healthbackend.controllers.patient;

import br.com.fiap.gs2023healthbackend.models.Patient;
import br.com.fiap.gs2023healthbackend.payload.response.patient.insurance.PatientResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/patients")
public class PatientController extends PatientBaseController {
    @GetMapping
    @PreAuthorize("hasRole('ROLE_PATIENT')")
    public ResponseEntity<PatientResponse> getPatientInfo() {
        Patient patient = getPatient();

        logger.info("Patient [%s] requested information".formatted(patient.getUsername()));

        PatientResponse response = getPatientResponse(patient);

        return ResponseEntity.ok(response);
    }
}
