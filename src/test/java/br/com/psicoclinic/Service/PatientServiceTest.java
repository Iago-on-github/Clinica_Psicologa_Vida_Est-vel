package br.com.psicoclinic.Service;

import br.com.psicoclinic.Exceptions.PatientNotFoundException;
import br.com.psicoclinic.Models.AppointmentScheduling;
import br.com.psicoclinic.Models.Doctor;
import br.com.psicoclinic.Models.Dtos.AppointmentResponseDto;
import br.com.psicoclinic.Models.Dtos.AppointmentSchedulingDto;
import br.com.psicoclinic.Models.Dtos.ResponseDoctorDto;
import br.com.psicoclinic.Models.Dtos.ResponsePatientDto;
import br.com.psicoclinic.Models.Patient;
import br.com.psicoclinic.Repositories.PatientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PatientServiceTest {
    @InjectMocks
    private PatientService patientService;
    @Mock
    private PatientRepository patientRepository;
    private Patient patient;
    @Captor
    private ArgumentCaptor<Patient> argumentCaptor;

    @Nested
    class getAllPatients {
        @Test
        @DisplayName("Should return all active patients with success")
        void ShouldReturnAllPatientWithSuccess() {
            Pageable pageable = PageRequest.of(0, 10);
            Page<Patient> patients = new PageImpl<>(List.of(
                    new Patient(1L, "name2", 18, 'M', "desc", true),
                    new Patient(2L, "name", 19, 'F', "desc", true),
                    new Patient(3L, "name3", 20, 'F', "desc", true)));


            when(patientRepository.findAll(pageable)).thenReturn(patients);

            Page<ResponsePatientDto> output = patientService.getAllPatients(pageable);

            assertNotNull(output);
            assertEquals(patients.getContent().size(), output.getContent().size());

            assertEquals(
                    patients.stream().map(Patient::getPatient_id).toList(),
                    output.stream().map(ResponsePatientDto::id).toList()
            );

            assertEquals(
                    patients.stream().map(Patient::isActive).toList(),
                    output.stream().map(ResponsePatientDto::patientActive).toList()
            );
        }

        @Test
        @DisplayName("Throws exception when error occurs in get all active patients")
        void ThrowsExceptionWhenErrorOccursInGetAllPatients() {
            Pageable pageable = PageRequest.of(0, 10);

            when(patientRepository.findAll(pageable)).thenThrow(new RuntimeException());

            assertThrows(RuntimeException.class, () -> patientService.getAllPatients(pageable));

            verify(patientRepository).findAll(pageable);
        }
    }

    @Nested
    class getPatientById {
        @Test
        @DisplayName("Should return active patient by your id")
        void ShouldReturnActivePatientByYourIdWithSuccess() {
            patient = new Patient(1L, "name2", 18, 'M', "desc", true);

            when(patientRepository.findById(patient.getPatient_id())).thenReturn(Optional.of(patient));

            var output = patientService.getPatientById(patient.getPatient_id());

            assertNotNull(output);

            assertEquals(patient.getPatient_id(), output.id());
            assertEquals(patient.getName(), output.name());
            assertEquals(patient.getAge(), output.age());
            assertEquals(patient.getGender(), output.gender());
            assertEquals(patient.getDescription(), output.description());
            assertEquals(patient.isActive(), output.patientActive());
        }

        @Test
        @DisplayName("Throws exception when error occurs in find active patients by id")
        void ThrowsExceptionWhenErrorOccursInFindById() {
            patient = new Patient(1L, "name2", 18, 'M', "desc", true);

            when(patientRepository.findById(patient.getPatient_id())).thenThrow(new PatientNotFoundException("Not found"));

            assertThrows(PatientNotFoundException.class, () -> patientService.getPatientById(patient.getPatient_id()));

            verify(patientRepository).findById(patient.getPatient_id());
        }
    }

    @Nested
    class createPatient {
        @Test
        @DisplayName("Should create Patent with success")
        void createPatientWithSuccess() {
            patient = new Patient(1L, "name", 18, 'M', "desc", true);
            var dto = createResponsePatientDto();

            patient.setName(dto.name());
            patient.setAge(dto.age());
            patient.setGender(dto.gender());
            patient.setDescription(dto.description());
            patient.setActive(true);

            when(patientRepository.save(any(Patient.class))).thenAnswer(invocation -> {
                Patient savedPatient = invocation.getArgument(0);
                savedPatient.setPatient_id(1L);
                return savedPatient;
            });

            when(patientRepository.findById(patient.getPatient_id())).thenReturn(Optional.of(patient));

            var output = patientService.createPatient(dto);

            assertNotNull(output);

            assertTrue(output.patientActive());

            assertEquals(patient.getPatient_id(), output.id());
            assertEquals(patient.getName(), output.name());
            assertEquals(patient.getAge(), output.age());
            assertEquals(patient.getGender(), output.gender());
            assertEquals(patient.getDescription(), output.description());
            assertEquals(
                    patient.getAppointments().stream().map(AppointmentScheduling::getSchedulingId).toList(),
                    output.appointments().stream().map(AppointmentResponseDto::schedulingId).toList());
        }
        @Test
        @DisplayName("Throws exception when error occurs in method create patient")
        void throwsExceptionWhenErrorOccursInCreatePatient() {
            patient = new Patient(1L, "name", 18, 'M', "desc", true);
            var dto = createResponsePatientDto();

            when(patientRepository.save(any(Patient.class))).thenThrow(new RuntimeException());
            when(patientRepository.findById(patient.getPatient_id())).thenThrow(new PatientNotFoundException("Not Found"));

            assertThrows(RuntimeException.class, () -> patientService.createPatient(dto));
            assertThrows(PatientNotFoundException.class, () -> patientService.getPatientById(patient.getPatient_id()));

            verify(patientRepository).findById(patient.getPatient_id());
        }
    }

    @Nested
    class updatePatient {
        @Test
        @DisplayName("Should update patient with success")
        void ShouldUpdatePatientWithSuccess() {
            patient = new Patient(1L, "name", 18, 'M', "desc", true);
            var dto = createResponsePatientDto();

            patient.setAge(dto.age());
            patient.setDescription(dto.description());
            patient.setActive(dto.patientActive());

            when(patientRepository.save(any(Patient.class))).thenReturn(patient);
            when(patientRepository.findById(patient.getPatient_id())).thenReturn(Optional.of(patient));

            var output = patientService.updatePatient(patient.getPatient_id(), dto);

            assertNotNull(output);

            assertEquals(patient.getPatient_id(), output.id());
            assertEquals(patient.getAge(), output.age());
            assertEquals(patient.getDescription(), output.description());
            assertEquals(patient.isActive(), output.patientActive());
        }

        @Test
        @DisplayName("Throws exception when error occurs in method update patient")
        void throwsExceptionWhenErrorOccursInUpdatePatient() {
            patient = new Patient(1L, "name", 18, 'M', "desc", true);
            var dto = createResponsePatientDto();

            when(patientRepository.findById(patient.getPatient_id())).thenThrow(new PatientNotFoundException("Not Found"));

            assertThrows(RuntimeException.class, () -> patientService.updatePatient(patient.getPatient_id(), dto));

            verify(patientRepository).findById(patient.getPatient_id());
        }
    }

    @Nested
    class inactivePatient{
        @Test
        @DisplayName("Should set status inactive in patient with success")
        void ShouldSetStatusInactiveInPatient() {
            Patient patientTest = new Patient();
            patientTest.setPatient_id(1L);
            patientTest.setActive(true);

            when(patientRepository.findById(patientTest.getPatient_id())).thenReturn(Optional.of(patientTest));

            patientService.inactivePatient(patientTest.getPatient_id());

            verify(patientRepository).findById(patientTest.getPatient_id());
            verify(patientRepository).save(argumentCaptor.capture());

            Patient capturedPatient = argumentCaptor.getValue();
            assertFalse(capturedPatient.isActive(), "Captured patient should be inactive");
        }

        @Test
        @DisplayName("Throws exception when error occurs in method inactive patient")
        void throwsExceptionWhenErrorOccursInInactivePatients() {
            Patient patientTest = new Patient();
            patientTest.setPatient_id(1L);
            patientTest.setActive(true);

            when(patientRepository.findById(patientTest.getPatient_id())).thenThrow(new PatientNotFoundException("Not Found"));

            assertThrows(PatientNotFoundException.class, () -> patientService.inactivePatient(patientTest.getPatient_id()));

        }
    }
    private ResponsePatientDto createResponsePatientDto() {
        List<AppointmentResponseDto> appointmentResponseDtoList = new ArrayList<>();
        AppointmentResponseDto appointmentResponseDto = new AppointmentResponseDto(
                1L,
                "patientName",
                "doctorName",
                null,
                true
        );
        appointmentResponseDtoList.add(appointmentResponseDto);

        return new ResponsePatientDto(1L, "name", 19, 'F', "desc", appointmentResponseDtoList,true);
    }
}