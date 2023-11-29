package br.com.fiap.gs2023healthbackend.controllers;

import br.com.fiap.gs2023healthbackend.enums.ERole;
import br.com.fiap.gs2023healthbackend.exceptions.InvalidSignupParameter;
import br.com.fiap.gs2023healthbackend.models.*;
import br.com.fiap.gs2023healthbackend.payload.request.LoginRequest;
import br.com.fiap.gs2023healthbackend.payload.request.signup.ClinicSignupRequest;
import br.com.fiap.gs2023healthbackend.payload.request.signup.MedicSignupRequest;
import br.com.fiap.gs2023healthbackend.payload.request.signup.PatientSignupRequest;
import br.com.fiap.gs2023healthbackend.payload.response.MessageResponse;
import br.com.fiap.gs2023healthbackend.payload.response.UserInfoResponse;
import br.com.fiap.gs2023healthbackend.repository.*;
import br.com.fiap.gs2023healthbackend.security.jwt.JwtUtils;
import br.com.fiap.gs2023healthbackend.security.services.UserDetailsImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.logging.Logger;
import java.util.stream.Collectors;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    Logger logger = Logger.getLogger(AuthController.class.getName());

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PatientRepository patientRepository;

    @Autowired
    MedicRepository medicRepository;

    @Autowired
    ClinicRepository clinicRepository;

    @Autowired
    LaboratoryRepository laboratoryRepository;

    @Autowired
    StateRepository stateRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    ObjectMapper objectMapper;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager
            .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        ResponseCookie jwtCookie = jwtUtils.generateJwtCookie(userDetails);

        List<String> roles = userDetails.getAuthorities().stream()
            .map(item -> item.getAuthority())
            .collect(Collectors.toList());

        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
            .body(new UserInfoResponse(userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                roles));
    }

    @PostMapping("/signout")
    public ResponseEntity<?> logoutUser() {
        ResponseCookie cookie = jwtUtils.getCleanJwtCookie();
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString())
            .body(new MessageResponse("You've been signed out!"));
    }

    @PostMapping("/signup/patient")
    public ResponseEntity<?> registerPatient(@Valid @RequestBody PatientSignupRequest patientData) {
        logger.info("Registering patient: " + patientData.getUsername());

        Set<Role> roles = roleRepository.findAllByNameIn(Set.of(ERole.ROLE_PATIENT, ERole.ROLE_USER));

        if (roles.isEmpty()) {
            return ResponseEntity.badRequest().body(new MessageResponse("Cannot find roles [%s] or [%s]".formatted(ERole.ROLE_PATIENT, ERole.ROLE_USER)));
        }

        Patient patient = Patient.builder()
            .username(patientData.getUsername())
            .email(patientData.getEmail())
            .password(encoder.encode(patientData.getPassword()))
            .name(patientData.getName())
            .lastName(patientData.getLastName())
            .cpf(patientData.getCpf())
            .rg(patientData.getRg())
            .roles(roles)
            .build();

        try {
            patientRepository.checkIfIsValidToCreate(patient);
        } catch (InvalidSignupParameter e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getErrorMessage()));
        }

        patientRepository.save(patient);

        return ResponseEntity.ok(new MessageResponse("Patient registered successfully!"));
    }

    @PostMapping("/signup/medic")
    public ResponseEntity<?> registerMedic(@Valid @RequestBody MedicSignupRequest medicData) {
        logger.info("Registering medic: " + medicData.getUsername());

        Optional<State> state = stateRepository.findByUf(medicData.getCrmUf().toUpperCase());

        if (state.isEmpty()) {
            return ResponseEntity.badRequest().body(new MessageResponse("CRM UF [%s] does not exist".formatted(medicData.getCrmUf().toUpperCase())));
        }

        Set<Role> roles = roleRepository.findAllByNameIn(Set.of(ERole.ROLE_MEDIC, ERole.ROLE_USER));

        if (roles.isEmpty()) {
            return ResponseEntity.badRequest().body(new MessageResponse("Cannot find roles [%s] or [%s]".formatted(ERole.ROLE_MEDIC, ERole.ROLE_USER)));
        }

        Medic medic = Medic.builder()
            .username(medicData.getUsername())
            .email(medicData.getEmail())
            .password(encoder.encode(medicData.getPassword()))
            .name(medicData.getName())
            .lastName(medicData.getLastName())
            .cpf(medicData.getCpf())
            .rg(medicData.getRg())
            .crm(medicData.getCrm())
            .crmState(state.get())
            .roles(roles)
            .build();

        try {
            medicRepository.checkIfIsValidToCreate(medic);
        } catch (InvalidSignupParameter e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getErrorMessage()));
        }

        medicRepository.save(medic);

        return ResponseEntity.ok(new MessageResponse("Medic registered successfully!"));
    }

    @PostMapping("/signup/clinic")
    public ResponseEntity<?> registerClinic(@Valid @RequestBody ClinicSignupRequest clinicData) {
        logger.info("Registering clinic: " + clinicData.getUsername());

        Set<Role> roles = roleRepository.findAllByNameIn(Set.of(ERole.ROLE_CLINIC, ERole.ROLE_USER));

        if (roles.isEmpty()) {
            return ResponseEntity.badRequest().body(new MessageResponse("Cannot find roles [%s] or [%s]".formatted(ERole.ROLE_CLINIC, ERole.ROLE_USER)));
        }

        Clinic clinic = Clinic.builder()
            .username(clinicData.getUsername())
            .email(clinicData.getEmail())
            .password(encoder.encode(clinicData.getPassword()))
            .name(clinicData.getName())
            .cnpj(clinicData.getCnpj())
            .cnes(clinicData.getCnes())
            .roles(roles)
            .build();

        try {
            clinicRepository.checkIfIsValidToCreate(clinic);
        } catch (InvalidSignupParameter e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getErrorMessage()));
        }

        clinicRepository.save(clinic);

        return ResponseEntity.ok(new MessageResponse("Clinic registered successfully!"));
    }

    @PostMapping("/signup/laboratory")
    public ResponseEntity<?> registerLaboratory(@Valid @RequestBody ClinicSignupRequest laboratoryData) {
        logger.info("Registering laboratory: " + laboratoryData.getUsername());

        Set<Role> roles = roleRepository.findAllByNameIn(Set.of(ERole.ROLE_LABORATORY, ERole.ROLE_USER));

        if (roles.isEmpty()) {
            return ResponseEntity.badRequest().body(new MessageResponse("Cannot find roles [%s] or [%s]".formatted(ERole.ROLE_LABORATORY, ERole.ROLE_USER)));
        }

        Laboratory laboratory = Laboratory.builder()
            .username(laboratoryData.getUsername())
            .email(laboratoryData.getEmail())
            .password(encoder.encode(laboratoryData.getPassword()))
            .name(laboratoryData.getName())
            .cnpj(laboratoryData.getCnpj())
            .cnes(laboratoryData.getCnes())
            .roles(roles)
            .build();

        try {
            laboratoryRepository.checkIfIsValidToCreate(laboratory);
        } catch (InvalidSignupParameter e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getErrorMessage()));
        }

        laboratoryRepository.save(laboratory);

        return ResponseEntity.ok(new MessageResponse("Laboratory registered successfully!"));
    }
}
