package br.com.fiap.gs2023healthbackend;

import br.com.fiap.gs2023healthbackend.enums.ERole;
import br.com.fiap.gs2023healthbackend.models.Role;
import br.com.fiap.gs2023healthbackend.models.State;
import br.com.fiap.gs2023healthbackend.repository.PatientRepository;
import br.com.fiap.gs2023healthbackend.repository.RoleRepository;
import br.com.fiap.gs2023healthbackend.repository.StateRepository;
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

    @Autowired
    StateRepository stateRepository;

    Logger logger = LoggerFactory.getLogger(Gs2023HealthBackendApplication.class);

    @EventListener(ApplicationReadyEvent.class)
    public void createRoles() {
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
    public void createStates() {
        if (!stateRepository.findAll().isEmpty()) {
            return;
        }

        State ac = State.builder().name("Acre").uf("AC").build();
        State al = State.builder().name("Alagoas").uf("AL").build();
        State ap = State.builder().name("Amapá").uf("AP").build();
        State am = State.builder().name("Amazonas").uf("AM").build();
        State ba = State.builder().name("Bahia").uf("BA").build();
        State ce = State.builder().name("Ceará").uf("CE").build();
        State df = State.builder().name("Distrito Federal").uf("DF").build();
        State es = State.builder().name("Espírito Santo").uf("ES").build();
        State go = State.builder().name("Goiás").uf("GO").build();
        State ma = State.builder().name("Maranhão").uf("MA").build();
        State mt = State.builder().name("Mato Grosso").uf("MT").build();
        State ms = State.builder().name("Mato Grosso do Sul").uf("MS").build();
        State mg = State.builder().name("Minas Gerais").uf("MG").build();
        State pb = State.builder().name("Paraíba").uf("PB").build();
        State pa = State.builder().name("Pará").uf("PA").build();
        State pr = State.builder().name("Paraná").uf("PR").build();
        State pe = State.builder().name("Pernambuco").uf("PE").build();
        State pi = State.builder().name("Piauí").uf("PI").build();
        State rj = State.builder().name("Rio de Janeiro").uf("RJ").build();
        State rn = State.builder().name("Rio Grande do Norte").uf("RN").build();
        State rs = State.builder().name("Rio Grande do Sul").uf("RS").build();
        State ro = State.builder().name("Rondônia").uf("RO").build();
        State rr = State.builder().name("Roraima").uf("RR").build();
        State sc = State.builder().name("Santa Catarina").uf("SC").build();
        State sp = State.builder().name("São Paulo").uf("SP").build();
        State se = State.builder().name("Sergipe").uf("SE").build();
        State to = State.builder().name("Tocantins").uf("TO").build();

        stateRepository.saveAll(Set.of(ac, al, ap, am, ba, ce, df, es, go, ma, mt, ms, mg, pb, pa, pr, pe, pi, rj, rn, rs, ro, rr, sc, sp, se, to));
    }

    public static void main(String[] args) {
        SpringApplication.run(Gs2023HealthBackendApplication.class, args);
    }
}
