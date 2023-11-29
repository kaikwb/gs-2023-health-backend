package br.com.fiap.gs2023healthbackend.controllers.clinic;

import br.com.fiap.gs2023healthbackend.enums.ERole;
import br.com.fiap.gs2023healthbackend.models.Clinic;
import br.com.fiap.gs2023healthbackend.models.Medic;
import br.com.fiap.gs2023healthbackend.models.MedicalSpeciality;
import br.com.fiap.gs2023healthbackend.payload.response.medic.MedicSimpleResponse;
import br.com.fiap.gs2023healthbackend.payload.response.clinic.ClinicResponse;
import br.com.fiap.gs2023healthbackend.repository.ClinicRepository;
import br.com.fiap.gs2023healthbackend.repository.MedicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;
import java.util.stream.Collectors;

@Component
public class ClinicBaseController {
    Logger logger = Logger.getLogger(getClass().getName());

    @Autowired
    ClinicRepository clinicRepository;

    @Autowired
    MedicRepository medicRepository;

    Boolean isClinic() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        return auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals(ERole.ROLE_CLINIC.name()));
    }

    Clinic getClinic() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        return clinicRepository.findByUsername(auth.getName()).orElseThrow();
    }

    MedicSimpleResponse getClinicMedicResponse(Medic medic) {
        return MedicSimpleResponse.builder()
            .id(medic.getId())
            .name(medic.getName())
            .lastName(medic.getLastName())
            .crm(medic.getCrm())
            .crmUf(medic.getCrmState().getName())
            .specialties(medic.getSpecialties().stream().map(MedicalSpeciality::getName).collect(Collectors.toSet()))
            .build();
    }

    ClinicResponse getClinicResponse(Clinic clinic) {
        return ClinicResponse.builder()
            .id(clinic.getId())
            .username(clinic.getUsername())
            .email(clinic.getEmail())
            .name(clinic.getName())
            .cnpj(clinic.getCnpj())
            .cnes(clinic.getCnes())
            .medics(clinic.getMedics().stream().map(this::getClinicMedicResponse).collect(Collectors.toSet()))
            .build();
    }
}
