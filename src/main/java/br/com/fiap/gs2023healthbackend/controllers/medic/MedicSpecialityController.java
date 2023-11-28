package br.com.fiap.gs2023healthbackend.controllers.medic;

import br.com.fiap.gs2023healthbackend.models.Medic;
import br.com.fiap.gs2023healthbackend.models.MedicalSpeciality;
import br.com.fiap.gs2023healthbackend.payload.request.medic.speciality.MedicalSpecialityRequest;
import br.com.fiap.gs2023healthbackend.payload.response.medic.speciality.MedicalSpecialityResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/medics/specialities")
public class MedicSpecialityController extends MedicBaseController {
    @Transactional
    @PreAuthorize("hasRole('ROLE_MEDIC')")
    @PutMapping
    public ResponseEntity<Void> updateSpecialties(@RequestBody @Valid MedicalSpecialityRequest specialitiesSet) {
        Medic medic = getMedic();

        Set<MedicalSpeciality> specialties = getMedicalSpecialities(specialitiesSet.getSpecialities());

        medic.setSpecialties(specialties);

        medicRepository.save(medic);

        logger.info("Medic [%s] updated specialities".formatted(medic.getUsername()));

        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasRole('ROLE_MEDIC')")
    @GetMapping
    public ResponseEntity<MedicalSpecialityResponse> getSpecialties() {
        Medic medic = getMedic();

        logger.info("Medic [%s] requested specialities".formatted(medic.getUsername()));

        MedicalSpecialityResponse response = MedicalSpecialityResponse.builder()
            .specialties(medic.getSpecialties().stream().map(MedicalSpeciality::getName).collect(Collectors.toSet()))
            .build();

        return ResponseEntity.ok(response);
    }
}
