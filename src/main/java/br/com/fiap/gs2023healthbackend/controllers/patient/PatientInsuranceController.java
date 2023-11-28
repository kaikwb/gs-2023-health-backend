package br.com.fiap.gs2023healthbackend.controllers.patient;

import br.com.fiap.gs2023healthbackend.models.MedicalInsurance;
import br.com.fiap.gs2023healthbackend.models.Patient;
import br.com.fiap.gs2023healthbackend.payload.request.patient.insurance.SetInsuranceRequest;
import br.com.fiap.gs2023healthbackend.payload.response.patient.insurance.InsuranceResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/patients/insurances")
public class PatientInsuranceController extends PatientBaseController {
    @PreAuthorize("hasRole('ROLE_PATIENT')")
    @GetMapping
    public ResponseEntity<InsuranceResponse> getInsurance() {
        Patient patient = getPatient();

        logger.info("Patient [%s] requested insurance information".formatted(patient.getUsername()));

        InsuranceResponse response = InsuranceResponse.builder()
            .insurance(patient.getInsurance() != null ? patient.getInsurance().getName() : null)
            .insuranceNumber(patient.getInsuranceNumber())
            .build();

        return ResponseEntity.ok(response);
    }

    @Transactional
    @PreAuthorize("hasRole('ROLE_PATIENT')")
    @PutMapping
    public ResponseEntity<InsuranceResponse> setInsurance(@RequestBody SetInsuranceRequest insuranceRequest) {
        Patient patient = getPatient();

        MedicalInsurance insurance = medicalInsuranceRepository.findByName(insuranceRequest.getInsurance()).orElseThrow();

        patient.setInsurance(insurance);
        patient.setInsuranceNumber(insuranceRequest.getInsuranceNumber());

        patientRepository.save(patient);

        logger.info("Patient [%s] updated insurance information".formatted(patient.getUsername()));

        InsuranceResponse response = InsuranceResponse.builder()
            .insurance(patient.getInsurance() != null ? patient.getInsurance().getName() : null)
            .insuranceNumber(patient.getInsuranceNumber())
            .build();

        return ResponseEntity.ok(response);
    }

    @Transactional
    @PreAuthorize("hasRole('ROLE_PATIENT')")
    @DeleteMapping
    public ResponseEntity<Void> deleteInsurance() {
        Patient patient = getPatient();

        patient.setInsurance(null);
        patient.setInsuranceNumber(null);

        patientRepository.save(patient);

        logger.info("Patient [%s] deleted insurance information".formatted(patient.getUsername()));

        return ResponseEntity.noContent().build();
    }
}
