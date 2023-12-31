package br.com.fiap.gs2023healthbackend.repository;

import br.com.fiap.gs2023healthbackend.enums.ERole;
import br.com.fiap.gs2023healthbackend.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole name);
    Set<Role> findAllByNameIn(Set<ERole> names);
}
