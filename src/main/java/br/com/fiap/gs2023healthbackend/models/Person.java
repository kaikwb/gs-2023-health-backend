package br.com.fiap.gs2023healthbackend.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(
    name = "persons",
    uniqueConstraints = {
        @UniqueConstraint(columnNames = "cpf", name = "uk_persons_cpf"),
        @UniqueConstraint(columnNames = "rg", name = "uk_persons_rg")
    }
)
public class Person extends User {
    @NotBlank
    @Column(name = "name", nullable = false)
    private String name;

    @NotBlank
    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "cpf")
    private String cpf;

    @NotBlank
    @Column(name = "rg", nullable = false)
    private String rg;

    public Person() {
    }

    public Person(String username, String email, String password, String name, String lastName, String cpf, String rg) {
        super(username, email, password);
        this.name = name;
        this.lastName = lastName;
        this.cpf = cpf;
        this.rg = rg;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getRg() {
        return rg;
    }

    public void setRg(String rg) {
        this.rg = rg;
    }
}
