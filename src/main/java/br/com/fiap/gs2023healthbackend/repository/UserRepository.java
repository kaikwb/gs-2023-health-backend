package br.com.fiap.gs2023healthbackend.repository;

import br.com.fiap.gs2023healthbackend.models.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends BaseUserRepository<User> {

}
