package br.com.fiap.gs2023healthbackend;

import br.com.fiap.gs2023healthbackend.enums.ERole;
import br.com.fiap.gs2023healthbackend.exceptions.InvalidSignupParameter;
import br.com.fiap.gs2023healthbackend.models.Patient;
import br.com.fiap.gs2023healthbackend.models.Role;
import br.com.fiap.gs2023healthbackend.repository.PatientRepository;
import br.com.fiap.gs2023healthbackend.repository.RoleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

import java.util.Set;

@SpringBootApplication
public class Gs2023HealthBackendApplication {
    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PatientRepository patientRepository;

    Logger logger = LoggerFactory.getLogger(Gs2023HealthBackendApplication.class);

    @EventListener(ApplicationReadyEvent.class)
    public void createBasicRoles() {
        if (!roleRepository.findAll().isEmpty()) {
            return;
        }

        Role userRole = new Role(ERole.ROLE_USER);
        Role adminRole = new Role(ERole.ROLE_ADMIN);
        Role patientRole = new Role(ERole.ROLE_PATIENT);
        Role medicRole = new Role(ERole.ROLE_MEDIC);
        Role clinicRole = new Role(ERole.ROLE_CLINIC);
        Role laboratoryRole = new Role(ERole.ROLE_LABORATORY);

        roleRepository.save(userRole);
        roleRepository.save(adminRole);
        roleRepository.save(patientRole);
        roleRepository.save(medicRole);
        roleRepository.save(clinicRole);
        roleRepository.save(laboratoryRole);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void createBasicPatients() {
        Role patientRole = roleRepository.findByName(ERole.ROLE_PATIENT).orElseThrow();
        Role userRole = roleRepository.findByName(ERole.ROLE_USER).orElseThrow();

        Patient patient = Patient.builder()
            .username("patient")
            .email("test@email.com")
            .password("123456")
            .name("Patient")
            .lastName("Test")
            .rg("123456789")
            .cpf("123456789")
            .roles(Set.of(patientRole, userRole))
            .build();

        try {
            patientRepository.checkIfIsValidToCreate(patient);
            patientRepository.save(patient);
        } catch (InvalidSignupParameter e) {
            logger.error(e.getErrorMessage());
        }

    }

    public static void main(String[] args) {
        SpringApplication.run(Gs2023HealthBackendApplication.class, args);
    }
}
