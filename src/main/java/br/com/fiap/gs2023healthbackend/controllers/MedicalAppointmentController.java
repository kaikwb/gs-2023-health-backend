package br.com.fiap.gs2023healthbackend.controllers;

import br.com.fiap.gs2023healthbackend.enums.ERole;
import br.com.fiap.gs2023healthbackend.models.Medic;
import br.com.fiap.gs2023healthbackend.models.MedicalAppointment;
import br.com.fiap.gs2023healthbackend.models.Patient;
import br.com.fiap.gs2023healthbackend.payload.request.AppointmentRequest;
import br.com.fiap.gs2023healthbackend.payload.response.appointments.AppointmentResponse;
import br.com.fiap.gs2023healthbackend.payload.response.appointments.AppointmentsResponse;
import br.com.fiap.gs2023healthbackend.repository.MedicRepository;
import br.com.fiap.gs2023healthbackend.repository.MedicalAppointmentRepository;
import br.com.fiap.gs2023healthbackend.repository.PatientRepository;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/appointments")
public class MedicalAppointmentController {
    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    MedicalAppointmentRepository medicalAppointmentRepository;

    @Autowired
    PatientRepository patientRepository;

    @Autowired
    MedicRepository medicRepository;

    Medic getMedicFromAuth() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        return medicRepository.findByUsername(auth.getName()).orElseThrow();
    }

    Patient getPatientFromAuth() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        return patientRepository.findByUsername(auth.getName()).orElseThrow();
    }

    Boolean isMedic() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        return auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals(ERole.ROLE_MEDIC.name()));
    }

    Boolean isPatient() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        return auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals(ERole.ROLE_PATIENT.name()));
    }

    Patient getPatientFromId(Long id) {
        return patientRepository.findById(id).orElseThrow();
    }

    AppointmentResponse getAppointmentResponse(MedicalAppointment appointment) {
        return AppointmentResponse.builder()
            .id(appointment.getId())
            .medic("%s %s".formatted(appointment.getMedic().getName(), appointment.getMedic().getLastName()))
            .patient("%s %s".formatted(appointment.getPatient().getName(), appointment.getPatient().getLastName()))
            .date(appointment.getDate())
            .description(appointment.getDescription())
            .build();
    }

    ResponseEntity<AppointmentsResponse> getMedicAppointments(Pageable pageable) {
        Medic medic = getMedicFromAuth();

        AppointmentsResponse response = AppointmentsResponse.builder()
            .appointments(medicalAppointmentRepository.findAllByMedicId(medic.getId(), pageable).stream().map(this::getAppointmentResponse).collect(Collectors.toSet()))
            .build();

        return ResponseEntity.ok(response);
    }

    ResponseEntity<AppointmentsResponse> getPatientAppointments(Pageable pageable) {
        Patient patient = getPatientFromAuth();

        AppointmentsResponse response = AppointmentsResponse.builder()
            .appointments(medicalAppointmentRepository.findAllByPatientId(patient.getId(), pageable).stream().map(this::getAppointmentResponse).collect(Collectors.toSet()))
            .build();

        return ResponseEntity.ok(response);
    }

    @Transactional
    @PostMapping
    @PreAuthorize("hasRole('ROLE_MEDIC')")
    public ResponseEntity<AppointmentResponse> createAppointment(@RequestBody @Valid AppointmentRequest appointmentRequest) {
        Medic medic = getMedicFromAuth();

        Patient patient = getPatientFromId(appointmentRequest.getPatient());

        MedicalAppointment appointment = MedicalAppointment.builder()
            .medic(medic)
            .patient(patient)
            .date(appointmentRequest.getDate())
            .description(appointmentRequest.getDescription())
            .build();

        medicalAppointmentRepository.save(appointment);

        logger.info("Medic [%s] created an appointment for patient [%s]".formatted(medic.getUsername(), patient.getUsername()));

        AppointmentResponse response = getAppointmentResponse(appointment);

        return ResponseEntity.created(URI.create("/api/appointments")).body(response);
    }

    @GetMapping
    @PreAuthorize("hasRole('ROLE_MEDIC') or hasRole('ROLE_PATIENT')")
    public ResponseEntity<AppointmentsResponse> getAppointments(@PageableDefault Pageable pageable) {
        if (Boolean.TRUE.equals(isMedic())) {
            return getMedicAppointments(pageable);
        }

        return getPatientAppointments(pageable);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_MEDIC') or hasRole('ROLE_PATIENT')")
    public ResponseEntity<AppointmentResponse> getAppointment(@PathVariable Long id) {
        MedicalAppointment appointment = medicalAppointmentRepository.findById(id).orElseThrow();

        if (Boolean.TRUE.equals(isMedic())) {
            Medic medic = getMedicFromAuth();

            if (!appointment.getMedic().getId().equals(medic.getId())) {
                return ResponseEntity.notFound().build();
            }
        }

        if (Boolean.TRUE.equals(isPatient())) {
            Patient patient = getPatientFromAuth();

            if (!appointment.getPatient().getId().equals(patient.getId())) {
                return ResponseEntity.notFound().build();
            }
        }

        AppointmentResponse response = getAppointmentResponse(appointment);

        return ResponseEntity.ok(response);
    }

    @Transactional
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_MEDIC')")
    public ResponseEntity<AppointmentResponse> updateAppointment(@PathVariable Long id, @RequestBody @Valid AppointmentRequest appointmentRequest) {
        MedicalAppointment appointment = medicalAppointmentRepository.findById(id).orElseThrow();

        Medic medic = getMedicFromAuth();

        if (!appointment.getMedic().getId().equals(medic.getId())) {
            return ResponseEntity.notFound().build();
        }

        Patient patient = getPatientFromId(appointmentRequest.getPatient());

        appointment.setPatient(patient);
        appointment.setDate(appointmentRequest.getDate());
        appointment.setDescription(appointmentRequest.getDescription());

        medicalAppointmentRepository.save(appointment);

        logger.info("Medic [%s] updated an appointment for patient [%s]".formatted(medic.getUsername(), patient.getUsername()));

        AppointmentResponse response = getAppointmentResponse(appointment);

        return ResponseEntity.ok(response);
    }

    @Transactional
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_MEDIC')")
    public ResponseEntity<Void> deleteAppointment(@PathVariable Long id) {
        MedicalAppointment appointment = medicalAppointmentRepository.findById(id).orElseThrow();

        Medic medic = getMedicFromAuth();

        if (!appointment.getMedic().getId().equals(medic.getId())) {
            return ResponseEntity.notFound().build();
        }

        medicalAppointmentRepository.delete(appointment);

        logger.info("Medic [%s] deleted an appointment for patient [%s]".formatted(medic.getUsername(), appointment.getPatient().getUsername()));

        return ResponseEntity.noContent().build();
    }
}
