package br.com.fiap.gs2023healthbackend.controllers.medic;

import br.com.fiap.gs2023healthbackend.models.Medic;
import br.com.fiap.gs2023healthbackend.payload.response.MedicResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/medics")
public class MedicController extends MedicBaseController {
    @GetMapping
    @PreAuthorize("hasRole('ROLE_MEDIC')")
    public ResponseEntity<MedicResponse> getMedicInfo() {
        Medic medic = getMedic();

        logger.info("Medic [%s] requested information".formatted(medic.getUsername()));

        MedicResponse response = getMedicResponse(medic);

        return ResponseEntity.ok(response);
    }
}
