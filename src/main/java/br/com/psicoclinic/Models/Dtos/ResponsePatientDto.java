package br.com.psicoclinic.Models.Dtos;

import br.com.psicoclinic.Models.AppointmentScheduling;

import java.util.List;

public record ResponsePatientDto(Long id,
                                 String name,
                                 Integer age,
                                 Character gender,
                                 String description,
                                 List<AppointmentResponseDto> appointments,
                                 boolean patientActive) {
}
