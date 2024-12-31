package br.com.psicoclinic.Models.Dtos;

import br.com.psicoclinic.Models.Doctor;
import br.com.psicoclinic.Models.Patient;

import java.time.LocalDateTime;

public record AppointmentSchedulingDto(
        Long schedulingId,
        Long patientId,
        Long doctorId,
        LocalDateTime scheduledTimeFor,
        Boolean schedulingActive){
}
