package br.com.psicoclinic.Service;

import br.com.psicoclinic.Exceptions.PatientNotFoundException;
import br.com.psicoclinic.Exceptions.PatientWithNoAppointmentHistory;
import br.com.psicoclinic.Models.AppointmentScheduling;
import br.com.psicoclinic.Models.Dtos.AppointmentHistoryDto;
import br.com.psicoclinic.Repositories.AppointmentHistoryRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class AppointmentHistoryService {
    private final AppointmentHistoryRepository appointmentHistoryRepository;

    public AppointmentHistoryService(AppointmentHistoryRepository appointmentHistoryRepository) {
        this.appointmentHistoryRepository = appointmentHistoryRepository;
    }

    public List<AppointmentHistoryDto> getHistoryByPatientId(Long id) {
        var appointments = appointmentHistoryRepository.findByPatientId(id);

        if (appointments.isEmpty()) throw new PatientWithNoAppointmentHistory("No appointments history about this patient.");

        appointments.stream()
                .filter(p -> p.getScheduledTimeFor().isBefore(LocalDateTime.now())).toList();

        return appointments.stream().map(p -> new AppointmentHistoryDto(
                p.getHistoryId(),
                p.getScheduledTimeFor(),
                p.getEndTime(),
                p.getDoctor().getName(),
                p.getPatient().getName())).toList();
    }
}
