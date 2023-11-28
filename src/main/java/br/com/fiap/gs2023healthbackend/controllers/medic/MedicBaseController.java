package br.com.fiap.gs2023healthbackend.controllers.medic;

import br.com.fiap.gs2023healthbackend.models.MedicalSpeciality;
import br.com.fiap.gs2023healthbackend.models.Medic;
import br.com.fiap.gs2023healthbackend.payload.response.MedicResponse;
import br.com.fiap.gs2023healthbackend.repository.MedicRepository;
import br.com.fiap.gs2023healthbackend.repository.MedicalSpecialityRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class MedicBaseController {
    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    MedicRepository medicRepository;

    @Autowired
    MedicalSpecialityRepository medicalSpecialityRepository;

    Medic getMedic() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        return medicRepository.findByUsername(auth.getName()).orElseThrow();
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
