package br.com.psicoclinic.Service;

import br.com.psicoclinic.Exceptions.DoctorNotFoundException;
import br.com.psicoclinic.Exceptions.PatientNotFoundException;
import br.com.psicoclinic.Models.AppointmentScheduling;
import br.com.psicoclinic.Models.Doctor;
import br.com.psicoclinic.Models.Dtos.AppointmentSchedulingDto;
import br.com.psicoclinic.Models.Patient;
import br.com.psicoclinic.Repositories.AppointmentSchedulingRepository;
import br.com.psicoclinic.Repositories.DoctorRepository;
import br.com.psicoclinic.Repositories.PatientRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AppointmentSchedulingServiceTest {
    @Mock
    private AppointmentSchedulingRepository appointmentSchedulingRepository;
    @Mock
    private PatientRepository patientRepository;
    @Mock
    private DoctorRepository doctorRepository;
    @InjectMocks
    private AppointmentSchedulingService appointmentSchedulingService;

    @Nested
    class createAppointmentScheduling {
        @Test
        @DisplayName("create appointment scheduling with success")
        void createAppointmentSchedulingWithSuccess() {
            AppointmentScheduling appointment = new AppointmentScheduling();
            Patient patient = new Patient();
            patient.setActive(true);
            Doctor doctor = new Doctor();

            AppointmentSchedulingDto appointmentSchedulingDto = new AppointmentSchedulingDto(
                    1L,
                    patient.getPatient_id(),
                    doctor.getDoctorId(),
                    LocalDateTime.now(),
                    true);

            ArgumentCaptor<LocalDateTime> captor = ArgumentCaptor.forClass(LocalDateTime.class);

            when(patientRepository.findById(patient.getPatient_id())).thenReturn(Optional.of(patient));
            when(doctorRepository.findByChooseAFreeRandomDoctor(captor.capture())).thenReturn(doctor);

            appointment.setDoctor(doctor);
            appointment.setPatient(patient);
            appointment.setScheduledTimeFor(appointmentSchedulingDto.scheduledTimeFor());

            when(appointmentSchedulingRepository.save(any(AppointmentScheduling.class))).thenReturn(appointment);

            var output = appointmentSchedulingService.create(appointmentSchedulingDto);

            assertNotNull(output);

            assertEquals(appointment.getSchedulingId(), output.schedulingId());

            verify(patientRepository).findById(patient.getPatient_id());

            assertTrue(appointment.getActive());
            assertTrue(appointment.getPatient().isActive());
            assertTrue(appointment.getDoctor().isActive());
        }

        @Test
        @DisplayName("Throws exception when error occurs in method create appointment scheduling ")
        void throwExceptionWhenErrorOccursInCreateAppointmentScheduling() {
            Patient patient = new Patient();
            patient.setActive(true);

            Doctor doctor = new Doctor();

            AppointmentSchedulingDto appointmentSchedulingDto = new AppointmentSchedulingDto(
                    1L,
                    1L,
                    1L,
                    LocalDateTime.now(),
                    true
            );

            when(patientRepository.findById(appointmentSchedulingDto.patientId()))
                    .thenReturn(Optional.of(patient));

            when(doctorRepository.findByChooseAFreeRandomDoctor(appointmentSchedulingDto.scheduledTimeFor()))
                    .thenReturn(doctor);

            when(appointmentSchedulingRepository.save(any(AppointmentScheduling.class)))
                    .thenThrow(new RuntimeException());

            assertThrows(RuntimeException.class,
                    () -> appointmentSchedulingService.create(appointmentSchedulingDto));

            verify(appointmentSchedulingRepository).save(any(AppointmentScheduling.class));
        }
    }
}