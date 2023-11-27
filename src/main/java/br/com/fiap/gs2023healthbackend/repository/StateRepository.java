package br.com.fiap.gs2023healthbackend.repository;

import br.com.fiap.gs2023healthbackend.models.State;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StateRepository extends JpaRepository<State, Long> {
    Optional<State> findByUf(String uf);

    Optional<State> findByName(String name);
}
