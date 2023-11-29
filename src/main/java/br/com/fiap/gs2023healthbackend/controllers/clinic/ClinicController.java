package br.com.fiap.gs2023healthbackend.controllers.clinic;

import br.com.fiap.gs2023healthbackend.models.Clinic;
import br.com.fiap.gs2023healthbackend.payload.response.ClinicsResponse;
import br.com.fiap.gs2023healthbackend.payload.response.clinic.ClinicResponse;
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
@RequestMapping("/api/clinics")
public class ClinicController extends ClinicBaseController {
    @GetMapping
    @PreAuthorize("hasRole('ROLE_CLINIC') or hasRole('ROLE_USER')")
    public ResponseEntity<?> getClinics(@PageableDefault(sort = {"name"}, size = 10) Pageable pageable) {
        if (Boolean.TRUE.equals(isClinic())) {
            return getClinicInfo();
        }

        return getClinicsInfo(pageable);
    }

    ResponseEntity<ClinicsResponse> getClinicsInfo(Pageable pageable) {
        Set<Clinic> clinics = clinicRepository.findAll(pageable).toSet();

        ClinicsResponse response = ClinicsResponse.builder()
            .clinics(clinics.stream().map(this::getClinicResponse).collect(Collectors.toSet()))
            .build();

        return ResponseEntity.ok(response);
    }

    ResponseEntity<ClinicResponse> getClinicInfo() {
        Clinic clinic = getClinic();

        logger.info("Clinic [%s] requested information".formatted(clinic.getUsername()));

        ClinicResponse response = getClinicResponse(clinic);

        return ResponseEntity.ok(response);
    }
}
