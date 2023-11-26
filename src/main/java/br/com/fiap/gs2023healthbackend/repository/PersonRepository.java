package br.com.fiap.gs2023healthbackend.repository;

import br.com.fiap.gs2023healthbackend.exceptions.InvalidSignupParameter;
import br.com.fiap.gs2023healthbackend.models.Person;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository<T extends Person> extends BaseUserRepository<T> {
    Boolean existsByCpf(String cpf);

    Boolean existsByRg(String rg);

    @Override
    default void checkIfIsValidToCreate(T person) throws InvalidSignupParameter {
        BaseUserRepository.super.checkIfIsValidToCreate(person);

        if (Boolean.TRUE.equals(existsByCpf(person.getCpf()))) {
            throw new InvalidSignupParameter("CPF [%s] already exists".formatted(person.getCpf()));
        }

        if (Boolean.TRUE.equals(existsByRg(person.getRg()))) {
            throw new InvalidSignupParameter("RG [%s] already exists".formatted(person.getRg()));
        }
    }
}
