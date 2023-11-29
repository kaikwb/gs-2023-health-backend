package br.com.fiap.gs2023healthbackend.controllers.medic;

import br.com.fiap.gs2023healthbackend.models.Medic;
import br.com.fiap.gs2023healthbackend.payload.response.MedicResponse;
import br.com.fiap.gs2023healthbackend.payload.response.medic.MedicsResponse;
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
@RequestMapping("/api/medics")
public class MedicController extends MedicBaseController {
    @GetMapping
    @PreAuthorize("hasRole('ROLE_MEDIC') or hasRole('ROLE_USER')")
    public ResponseEntity<?> getMedics(@PageableDefault(sort = {"name", "lastName"}, size = 10) Pageable pageable) {
        if (Boolean.TRUE.equals(isMedic())) {
            return getMedicInfo();
        }

        return getMedicsInfo(pageable);
    }

    ResponseEntity<MedicsResponse> getMedicsInfo(Pageable pageable) {
        Set<Medic> medics = medicRepository.findAll(pageable).toSet();

        MedicsResponse response = MedicsResponse.builder()
            .medics(medics.stream().map(this::getMedicSimpleResponse).collect(Collectors.toSet()))
            .build();

        return ResponseEntity.ok(response);
    }

    ResponseEntity<MedicResponse> getMedicInfo() {
        Medic medic = getMedic();

        logger.info("Medic [%s] requested information".formatted(medic.getUsername()));

        MedicResponse response = getMedicResponse(medic);

        return ResponseEntity.ok(response);
    }
}
