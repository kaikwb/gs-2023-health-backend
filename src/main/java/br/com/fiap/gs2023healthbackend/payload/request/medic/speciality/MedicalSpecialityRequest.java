package br.com.fiap.gs2023healthbackend.payload.request.medic.speciality;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Set;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MedicalSpecialityRequest {
    private Set<String> specialities;
}
