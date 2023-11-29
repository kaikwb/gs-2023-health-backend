package br.com.fiap.gs2023healthbackend.controllers.medic;

import br.com.fiap.gs2023healthbackend.enums.ERole;
import br.com.fiap.gs2023healthbackend.models.MedicalSpeciality;
import br.com.fiap.gs2023healthbackend.models.Medic;
import br.com.fiap.gs2023healthbackend.payload.response.MedicResponse;
import br.com.fiap.gs2023healthbackend.payload.response.medic.MedicSimpleResponse;
import br.com.fiap.gs2023healthbackend.repository.MedicRepository;
import br.com.fiap.gs2023healthbackend.repository.MedicalSpecialityRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class MedicBaseController {
    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    MedicRepository medicRepository;

    @Autowired
    MedicalSpecialityRepository medicalSpecialityRepository;

    Boolean isMedic() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        return auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals(ERole.ROLE_MEDIC.name()));
    }

    Medic getMedic() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        return medicRepository.findByUsername(auth.getName()).orElseThrow();
    }

    MedicSimpleResponse getMedicSimpleResponse(Medic medic) {
        return MedicSimpleResponse.builder()
            .id(medic.getId())
            .name(medic.getName())
            .lastName(medic.getLastName())
            .crm(medic.getCrm())
            .crmUf(medic.getCrmState().getName())
            .specialties(medic.getSpecialties().stream().map(MedicalSpeciality::getName).collect(Collectors.toSet()))
            .build();
    }

    MedicResponse getMedicResponse(Medic medic) {
        return MedicResponse.builder()
            .id(medic.getId())
            .username(medic.getUsername())
            .email(medic.getEmail())
            .name(medic.getName())
            .lastName(medic.getLastName())
            .cpf(medic.getCpf())
            .rg(medic.getRg())
            .crm(medic.getCrm())
            .crmUf(medic.getCrmState().getName())
            .specialties(medic.getSpecialties().stream().map(MedicalSpeciality::getName).toList())
            .build();
    }

    Set<MedicalSpeciality> getMedicalSpecialities(Set<String> names) {
        return medicalSpecialityRepository.findAllByNameIn(names);
    }
}
