package br.com.psicoclinic.Models.Dtos;

import br.com.psicoclinic.Models.Enumn.Speciality;

import java.util.List;

public record ResponseDoctorDto(Long id,
                                String name,
                                Speciality speciality,
                                Character gender,
                                String crm,
                                String description,
                                List<AppointmentResponseDto> appointments,
                                boolean active){
}
