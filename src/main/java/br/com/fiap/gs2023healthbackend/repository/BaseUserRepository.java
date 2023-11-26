package br.com.fiap.gs2023healthbackend.repository;

import br.com.fiap.gs2023healthbackend.exceptions.InvalidSignupParameter;
import br.com.fiap.gs2023healthbackend.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BaseUserRepository<T extends User> extends JpaRepository<T, Long> {
    Optional<T> findByUsername(String username);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);

    default void checkIfIsValidToCreate(T user) throws InvalidSignupParameter {
        if (Boolean.TRUE.equals(existsByUsername(user.getUsername()))) {
            throw new InvalidSignupParameter("Username [%s] already exists".formatted(user.getUsername()));
        }

        if (Boolean.TRUE.equals(existsByEmail(user.getEmail()))) {
            throw new InvalidSignupParameter("Email [%s] already exists".formatted(user.getEmail()));
        }
    }
}
