package br.com.psicoclinic.Service;

import br.com.psicoclinic.Models.AppointmentHistory;
import br.com.psicoclinic.Models.Doctor;
import br.com.psicoclinic.Models.Dtos.AppointmentHistoryDto;
import br.com.psicoclinic.Models.Patient;
import br.com.psicoclinic.Repositories.AppointmentHistoryRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AppointmentHistoryServiceTest {
    @Mock
    private AppointmentHistoryRepository appointmentHistoryRepository;
    @InjectMocks
    private AppointmentHistoryService appointmentHistoryService;

    @Nested
    class getHistoryByPatientId {
        @Test
        @DisplayName("Should return appointment history by patient id")
        void ShouldReturnAppointmentHistoryByPatientIdWithSuccess() {
            AppointmentHistory history = new AppointmentHistory(
                    1,
                    LocalDateTime.now(),
                    null,
                    new Doctor(),
                    new Patient(),
                    "Description"
            );
            List<AppointmentHistory> appointmentHistory = List.of(history);
            long id = 1;

            when(appointmentHistoryRepository.findByPatientId(id)).thenReturn(appointmentHistory);

            List<AppointmentHistoryDto> output = appointmentHistoryService.getHistoryByPatientId(id);

            assertNotNull(output);

            assertEquals(appointmentHistory.size(), output.size());
            assertEquals(
                    appointmentHistory.stream().map(AppointmentHistory::getHistoryId).toList(),
                    output.stream().map(AppointmentHistoryDto::historyId).toList());
        }
    }
}