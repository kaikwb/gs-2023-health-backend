package br.com.fiap.gs2023healthbackend.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/test")
public class TestController {
    @GetMapping("/all")
    public String allAccess() {
        return "Public Content.";
    }

    @GetMapping("/user")
    @PreAuthorize("hasRole('USER')")
    public String userAccess() {
        return "User Content.";
    }

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public String adminAccess() {
        return "Admin Board.";
    }

    @GetMapping("/patient")
    @PreAuthorize("hasRole('PATIENT')")
    public String patientAccess() {
        return "Patient Board.";
    }

    @GetMapping("/medic")
    @PreAuthorize("hasRole('medic')")
    public String medicAccess() {
        return "Medic Board.";
    }

    @GetMapping("/clinic")
    @PreAuthorize("hasRole('CLINIC')")
    public String clinicAccess() {
        return "Clinic Board.";
    }

    @GetMapping("/laboratory")
    @PreAuthorize("hasRole('LABORATORY')")
    public String laboratoryAccess() {
        return "Laboratory Board.";
    }
}