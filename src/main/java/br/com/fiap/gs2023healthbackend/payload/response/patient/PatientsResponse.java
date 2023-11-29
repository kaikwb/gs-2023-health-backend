package br.com.fiap.gs2023healthbackend.payload.response.patient;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Set;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PatientsResponse {
    Set<PatientSimpleResponse> patients;
}
