package br.com.fiap.gs2023healthbackend.repository;

import br.com.fiap.gs2023healthbackend.exceptions.InvalidSignupParameter;
import br.com.fiap.gs2023healthbackend.models.Medic;
import br.com.fiap.gs2023healthbackend.models.Uf;
import org.springframework.stereotype.Repository;

@Repository
public interface MedicRepository extends PersonRepository<Medic> {
    Boolean existsByCrmAndCrmUF(String crm, Uf crmUF);

    @Override
    default void checkIfIsValidToCreate(Medic medic) throws InvalidSignupParameter {
        PersonRepository.super.checkIfIsValidToCreate(medic);

        if (Boolean.TRUE.equals(existsByCrmAndCrmUF(medic.getCrm(), medic.getCrmUF()))) {
            throw new InvalidSignupParameter("CRM [%s-%s] already exists".formatted(medic.getCrm(), medic.getCrmUF().getName()));
        }
    }
}
