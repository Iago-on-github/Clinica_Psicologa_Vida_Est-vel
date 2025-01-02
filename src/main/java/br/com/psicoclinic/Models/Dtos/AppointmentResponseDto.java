package br.com.psicoclinic.Models.Dtos;

import br.com.psicoclinic.Models.Doctor;
import br.com.psicoclinic.Models.Patient;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public record AppointmentResponseDto(Long schedulingId,
                                     String patientName,
                                     String doctorName,
                                     @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
                                     LocalDateTime scheduledTimeFor,
                                     boolean activeAppointment) {
}
