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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

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
            Pageable pageable = PageRequest.of(0, 10);
            AppointmentHistory history = new AppointmentHistory(
                    1,
                    LocalDateTime.now(),
                    null,
                    new Doctor(),
                    new Patient(),
                    "Description"
            );
            Page<AppointmentHistory> appointmentHistory = new PageImpl<>(List.of(history));

            long id = 1;

            when(appointmentHistoryRepository.findByPatientId(id, pageable)).thenReturn(appointmentHistory);

            Page<AppointmentHistoryDto> output = appointmentHistoryService.getHistoryByPatientId(id, pageable);

            assertNotNull(output);

            assertEquals(appointmentHistory.getContent().size(), output.getContent().size());
            assertEquals(
                    appointmentHistory.stream().map(AppointmentHistory::getHistoryId).toList(),
                    output.stream().map(AppointmentHistoryDto::historyId).toList());
        }
    }
}