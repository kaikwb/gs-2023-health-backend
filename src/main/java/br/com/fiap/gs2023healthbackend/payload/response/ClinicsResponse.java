package br.com.fiap.gs2023healthbackend.payload.response;

import br.com.fiap.gs2023healthbackend.payload.response.clinic.ClinicResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Set;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClinicsResponse {
    Set<ClinicResponse> clinics;
}
