package br.com.fiap.gs2023healthbackend.controllers.clinic;

import br.com.fiap.gs2023healthbackend.models.Clinic;
import br.com.fiap.gs2023healthbackend.payload.response.clinic.ClinicResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/clinics")
public class ClinicController extends ClinicBaseController {
    @GetMapping
    @PreAuthorize("hasRole('ROLE_CLINIC')")
    public ResponseEntity<ClinicResponse> getClinicInfo() {
        Clinic clinic = getClinic();

        logger.info("Clinic [%s] requested information".formatted(clinic.getUsername()));

        ClinicResponse response = getClinicResponse(clinic);

        return ResponseEntity.ok(response);
    }
}
