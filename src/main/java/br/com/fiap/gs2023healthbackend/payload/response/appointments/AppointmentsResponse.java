package br.com.fiap.gs2023healthbackend.payload.response.appointments;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Set;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AppointmentsResponse {
    Set<AppointmentResponse> appointments;
}
