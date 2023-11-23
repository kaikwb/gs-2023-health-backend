package br.com.fiap.gs2023healthbackend;

import br.com.fiap.gs2023healthbackend.models.ERole;
import br.com.fiap.gs2023healthbackend.models.Role;
import br.com.fiap.gs2023healthbackend.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

@SpringBootApplication
public class Gs2023HealthBackendApplication {
    @Autowired
    RoleRepository roleRepository;

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

    public static void main(String[] args) {
        SpringApplication.run(Gs2023HealthBackendApplication.class, args);
    }
}
