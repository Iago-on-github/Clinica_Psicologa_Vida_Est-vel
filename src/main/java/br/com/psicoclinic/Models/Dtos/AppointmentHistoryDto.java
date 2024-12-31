package br.com.psicoclinic.Models.Dtos;

import br.com.psicoclinic.Models.Doctor;
import br.com.psicoclinic.Models.Patient;

import java.time.LocalDateTime;

public record AppointmentHistoryDto(Long historyId,
                                    LocalDateTime scheduledTimeFor,
                                    LocalDateTime endTime,
                                    String doctorName,
                                    String patientName) {
}
