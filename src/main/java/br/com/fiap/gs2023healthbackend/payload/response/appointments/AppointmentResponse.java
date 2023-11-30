package br.com.fiap.gs2023healthbackend.payload.response.appointments;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.util.StdDateFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AppointmentResponse {
    @NotNull
    private Long id;

    @NotBlank
    private String patient;

    @NotBlank
    private String medic;

    @Null
    @JsonFormat(pattern = StdDateFormat.DATE_FORMAT_STR_ISO8601, shape = JsonFormat.Shape.STRING)
    private ZonedDateTime date;

    @NotBlank
    private String description;
}
