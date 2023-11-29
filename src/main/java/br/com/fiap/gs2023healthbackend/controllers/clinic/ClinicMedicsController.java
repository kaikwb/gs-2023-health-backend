package br.com.fiap.gs2023healthbackend.controllers.clinic;

import br.com.fiap.gs2023healthbackend.models.Clinic;
import br.com.fiap.gs2023healthbackend.models.Medic;
import br.com.fiap.gs2023healthbackend.payload.response.clinic.ClinicResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/clinics/medics")
public class ClinicMedicsController extends ClinicBaseController {
    @Transactional
    @PutMapping("/{medicId}")
    public ResponseEntity<ClinicResponse> addMedic(@PathVariable Long medicId) {
        Clinic clinic = getClinic();

        Medic medic = medicRepository.findById(medicId).orElseThrow();

        clinic.getMedics().add(medic);

        clinicRepository.save(clinic);

        logger.info("Clinic [%s] added medic [%s]".formatted(clinic.getUsername(), medic.getUsername()));

        ClinicResponse response = getClinicResponse(clinic);

        return ResponseEntity.ok(response);
    }

    @Transactional
    @DeleteMapping("/{medicId}")
    public ResponseEntity<ClinicResponse> removeMedic(@PathVariable Long medicId) {
        Clinic clinic = getClinic();

        Medic medic = medicRepository.findById(medicId).orElseThrow();

        clinic.getMedics().remove(medic);

        clinicRepository.save(clinic);

        logger.info("Clinic [%s] removed medic [%s]".formatted(clinic.getUsername(), medic.getUsername()));

        ClinicResponse response = getClinicResponse(clinic);

        return ResponseEntity.ok(response);
    }
}
