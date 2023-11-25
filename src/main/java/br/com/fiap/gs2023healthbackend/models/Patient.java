package br.com.fiap.gs2023healthbackend.models;

import jakarta.persistence.*;

@Entity
@Table(
    name = "patients"
)
public class Patient extends Person {
    public Patient() {
    }

    public Patient(String username, String email, String password, String name, String lastName, String cpf, String rg) {
        super(username, email, password, name, lastName, cpf, rg);
    }
}

