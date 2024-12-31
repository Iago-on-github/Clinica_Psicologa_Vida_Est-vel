package br.com.psicoclinic.util;

import br.com.psicoclinic.Models.AppointmentHistory;
import br.com.psicoclinic.Models.AppointmentScheduling;
import br.com.psicoclinic.Repositories.AppointmentHistoryRepository;
import br.com.psicoclinic.Repositories.AppointmentSchedulingRepository;
import jakarta.transaction.Transactional;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ExpiredAppointments {
    private final AppointmentSchedulingRepository repository;
    private final AppointmentHistoryRepository appointmentHistoryRepository;

    public ExpiredAppointments(AppointmentSchedulingRepository repository, AppointmentHistoryRepository appointmentHistoryRepository) {
        this.repository = repository;
        this.appointmentHistoryRepository = appointmentHistoryRepository;
    }

    @Scheduled(fixedRate = 60000)
    @Transactional
    public void processExpiredAppointments() {
        LocalDateTime now = LocalDateTime.now();

        List<AppointmentScheduling> expiredAppointments = repository
                .findByActiveAndScheduledTimeForBefore(true, now.minusMinutes(120));

        if (expiredAppointments.isEmpty()) {
            System.out.println("No expired appointments to process at " + now);
            return;
        }

        List<AppointmentHistory> histories = new ArrayList<>();

        for (AppointmentScheduling app : expiredAppointments) {
            AppointmentHistory history = new AppointmentHistory();

            history.setDoctor(app.getDoctor());
            history.setPatient(app.getPatient());
            history.setScheduledTimeFor(app.getScheduledTimeFor());
            history.setEndTime(app.getScheduledTimeFor().plusHours(2));

            histories.add(history);

            app.setActive(false);
        }

        appointmentHistoryRepository.saveAll(histories);

        repository.saveAll(expiredAppointments);

        System.out.println("Processed " + expiredAppointments.size() + " expired appointments at " + now);
    }
}
