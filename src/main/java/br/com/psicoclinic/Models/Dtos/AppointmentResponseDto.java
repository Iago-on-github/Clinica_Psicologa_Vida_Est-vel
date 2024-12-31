package br.com.psicoclinic.Models.Dtos;

import br.com.psicoclinic.Models.Doctor;
import br.com.psicoclinic.Models.Patient;

import java.time.LocalDateTime;

public record AppointmentResponseDto(Long schedulingId,
                                     String patientName,
                                     String doctorName,
                                     LocalDateTime scheduledTimeFor,
                                     boolean activeAppointment) {
}
