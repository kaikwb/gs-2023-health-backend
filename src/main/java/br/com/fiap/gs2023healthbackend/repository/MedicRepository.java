package br.com.fiap.gs2023healthbackend.repository;

import br.com.fiap.gs2023healthbackend.exceptions.InvalidSignupParameter;
import br.com.fiap.gs2023healthbackend.models.Medic;
import br.com.fiap.gs2023healthbackend.models.State;
import org.springframework.stereotype.Repository;

@Repository
public interface MedicRepository extends PersonRepository<Medic> {
    Boolean existsByCrmAndCrmState(String crm, State crmState);

    @Override
    default void checkIfIsValidToCreate(Medic medic) throws InvalidSignupParameter {
        PersonRepository.super.checkIfIsValidToCreate(medic);

        if (Boolean.TRUE.equals(existsByCrmAndCrmState(medic.getCrm(), medic.getCrmState()))) {
            throw new InvalidSignupParameter("CRM [%s-%s] already exists".formatted(medic.getCrm(), medic.getCrmState().getUf()));
        }
    }
}
