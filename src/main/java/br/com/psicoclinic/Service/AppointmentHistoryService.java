package br.com.psicoclinic.Service;

import br.com.psicoclinic.Exceptions.PatientNotFoundException;
import br.com.psicoclinic.Exceptions.PatientWithNoAppointmentHistory;
import br.com.psicoclinic.Models.AppointmentScheduling;
import br.com.psicoclinic.Models.Dtos.AppointmentHistoryDto;
import br.com.psicoclinic.Repositories.AppointmentHistoryRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class AppointmentHistoryService {
    private final AppointmentHistoryRepository appointmentHistoryRepository;

    public AppointmentHistoryService(AppointmentHistoryRepository appointmentHistoryRepository) {
        this.appointmentHistoryRepository = appointmentHistoryRepository;
    }

    public Page<AppointmentHistoryDto> getHistoryByPatientId(Long id, Pageable pageable) {
        var appointments = appointmentHistoryRepository.findByPatientId(id, pageable);

        if (appointments.isEmpty()) throw new PatientWithNoAppointmentHistory("No appointments history about this patient.");

        appointments.stream()
                .filter(p -> p.getScheduledTimeFor().isBefore(LocalDateTime.now())).toList();

        return appointments.map(p -> new AppointmentHistoryDto(
                p.getHistoryId(),
                p.getScheduledTimeFor(),
                p.getEndTime(),
                p.getDoctor().getName(),
                p.getPatient().getName()));
    }
}
