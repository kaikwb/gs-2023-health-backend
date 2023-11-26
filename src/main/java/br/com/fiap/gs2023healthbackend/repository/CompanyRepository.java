package br.com.fiap.gs2023healthbackend.repository;

import br.com.fiap.gs2023healthbackend.exceptions.InvalidSignupParameter;
import br.com.fiap.gs2023healthbackend.models.Company;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyRepository<T extends Company> extends BaseUserRepository<T> {
    Boolean existsByCnpj(String cnpj);

    Boolean existsByCnes(String cnes);

    @Override
    default void checkIfIsValidToCreate(T company) throws InvalidSignupParameter {
        BaseUserRepository.super.checkIfIsValidToCreate(company);

        if (Boolean.TRUE.equals(existsByCnpj(company.getCnpj()))) {
            throw new InvalidSignupParameter("CNPJ [%s] already exists".formatted(company.getCnpj()));
        }

        if (Boolean.TRUE.equals(existsByCnes(company.getCnes()))) {
            throw new InvalidSignupParameter("CNES [%s] already exists".formatted(company.getCnes()));
        }
    }
}
