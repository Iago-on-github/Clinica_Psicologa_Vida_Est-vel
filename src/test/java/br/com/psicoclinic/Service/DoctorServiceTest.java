package br.com.psicoclinic.Service;

import br.com.psicoclinic.Exceptions.DoctorNotFoundException;
import br.com.psicoclinic.Models.AppointmentScheduling;
import br.com.psicoclinic.Models.Doctor;
import br.com.psicoclinic.Models.Dtos.AppointmentResponseDto;
import br.com.psicoclinic.Models.Dtos.ResponseDoctorDto;
import br.com.psicoclinic.Models.Enumn.Speciality;
import br.com.psicoclinic.Models.Patient;
import br.com.psicoclinic.Repositories.DoctorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DoctorServiceTest {
    @InjectMocks
    private DoctorService doctorService;
    @Mock
    private DoctorRepository doctorRepository;
    private Doctor doctor;
    private ResponseDoctorDto doctorDto;

    @BeforeEach
    void setUp() {
        doctor = createDoctorWithAppointments();
        doctorDto = createDoctorDto();
    }

    @Nested
    class getAllDoctors {
        @Test
        @DisplayName("Should return all Active Doctors")
        void ShouldReturnAllActiveDoctorsWithSuccess() {
            when(doctorRepository.findAll()).thenReturn(List.of(doctor));

            List<ResponseDoctorDto> output = doctorService.getAllDoctors();

            assertNotNull(output);
            assertEquals(1, output.size());

            assertEquals(1L, doctor.getDoctorId());
            assertEquals("Doctor", doctor.getName());
            assertEquals(Speciality.CHILD_PSYCHOLOGY, doctor.getSpeciality());
            assertEquals('M', doctor.getGender());
            assertEquals("123456", doctor.getCrm());
            assertEquals("Experience with infantil psycholigy", doctor.getDescription());
            assertTrue(doctor.isActive());

            assertNotNull(doctor.getAppointments());
            assertEquals(2, doctor.getAppointments().size());
        }

        @Test
        @DisplayName("Throws Exception When Error Occurs in Get All Active Doctors")
        void ThrowsExceptionWhenErrorOccursInGetAllActiveDoctors() {
            when(doctorRepository.findAll()).thenThrow(new RuntimeException());

            assertThrows(RuntimeException.class, () -> doctorService.getAllDoctors());
        }

    }

    @Nested
    class getDoctorsById {
        @Test
        @DisplayName("Should return active doctor by id")
        void ShouldReturnActiveDoctorById() {
            long id = 1;

            when(doctorRepository.findById(id)).thenReturn(Optional.of(doctor));

            ResponseDoctorDto output = doctorService.getDoctorById(id);

            assertNotNull(output, "output should not be null");

            assertEquals(doctor.getDoctorId(), output.id());
            assertEquals(doctor.getName(), output.name());
            assertEquals(doctor.getSpeciality(), output.speciality());
            assertEquals(doctor.getGender(), output.gender());
            assertEquals(doctor.getCrm(), output.crm());
            assertEquals(doctor.getDescription(), output.description());
            assertTrue(doctor.isActive(), "Doctor needs to be active");

            assertNotNull(doctor.getAppointments());
        }

        @Test
        @DisplayName("Throws exception when error occurs trying to get the doctor by id")
        void ThrowsExceptionWhenErrorOccursInGetDoctorById() {
            long id = 1;

            when(doctorRepository.findById(id)).thenThrow(new DoctorNotFoundException("Doctor not found."));

            assertThrows(DoctorNotFoundException.class, () -> doctorService.getDoctorById(id),
                    "Should exception if error occurs");
        }
    }

    @Nested
    class getDoctorBySpecialty{
        @Test
        @DisplayName("Should return active doctor by specialty")
        void ShouldReturnDoctorBySpecialty() {
           when(doctorRepository.findDoctorBySpeciality(Speciality.CHILD_PSYCHOLOGY)).thenReturn(List.of(doctor));

           List<Doctor> output = doctorRepository.findDoctorBySpeciality(Speciality.CHILD_PSYCHOLOGY);

           assertNotNull(output, "output should be not null");

           assertEquals(1, output.size());
           assertEquals(Speciality.CHILD_PSYCHOLOGY, output.get(0).getSpeciality());
        }

        @Test
        @DisplayName("Throws Exception when error occurs trying to get the Doctor by specialty")
        void ThrowsExceptionWhenErrorOccursInGetDoctorBySpecialty() {
            when(doctorRepository.findDoctorBySpeciality(Speciality.CHILD_PSYCHOLOGY))
                    .thenThrow(new DoctorNotFoundException("not found"));

            assertThrows(DoctorNotFoundException.class, () -> doctorService.getDoctorBySpeciality(Speciality.CHILD_PSYCHOLOGY));
        }
    }

    @Nested
    class createDoctor {
        @Test
        @DisplayName("Should create doctor with success")
        void ShouldCreateDoctorWithSuccess() {
            ResponseDoctorDto doctorDto = new ResponseDoctorDto(
                    null,
                    "Doctor",
                    Speciality.CHILD_PSYCHOLOGY,
                    'F',
                    "135453",
                    "Experience with infantil psycholigy",
                    null,
                    true, doctor.getLinks());

            Doctor doctor = new Doctor();
            doctor.setName(doctorDto.name());
            doctor.setSpeciality(doctorDto.speciality());
            doctor.setGender(doctorDto.gender());
            doctor.setCrm(doctorDto.crm());
            doctor.setDescription(doctorDto.description());

            when(doctorRepository.save(any(Doctor.class))).thenAnswer(invocation -> {
                Doctor savedDoctor = invocation.getArgument(0);
                savedDoctor.setDoctorId(1L);
                return savedDoctor;
            });

            AppointmentScheduling appointment = new AppointmentScheduling();
            when(doctorRepository.findById(1L)).thenReturn(Optional.of(doctor));

            ResponseDoctorDto output = doctorService.createDoctor(doctorDto);

            assertNotNull(output);
            assertEquals(1L, output.id());
            assertEquals("Doctor", output.name());
            assertEquals(Speciality.CHILD_PSYCHOLOGY, output.speciality());
            assertEquals('F', output.gender());
            assertEquals("135453", output.crm());
            assertEquals("Experience with infantil psycholigy", output.description());
            assertTrue(output.active(), "Doctor should be active");
        }

        @Test
        @DisplayName("Throws Exception when error occurs trying to create user")
        void ThrowsExceptionWhenErrorOccursInCreateUser() {
            ResponseDoctorDto doctorDto = new ResponseDoctorDto(
                    null,
                    "Doctor",
                    Speciality.CHILD_PSYCHOLOGY,
                    'F',
                    "135453",
                    "Experience with infantil psycholigy",
                    null,
                    true, doctor.getLinks());
            when(doctorRepository.save(any(Doctor.class))).thenThrow(new RuntimeException());

            assertThrows(RuntimeException.class, () -> doctorService.createDoctor(doctorDto));
        }
    }

    @Nested
    class updateDoctor{
        @Test
        @DisplayName("Should update doctor with success")
        void ShouldUpdateDoctorWithSuccess() {
            doctor.setName(doctorDto.name());
            doctor.setGender(doctorDto.gender());
            doctor.setDescription(doctorDto.description());

            when(doctorRepository.findById(doctor.getDoctorId())).thenReturn(Optional.of(doctor));
            when(doctorRepository.save(any(Doctor.class))).thenReturn(doctor);

            var output = doctorService.updateDoctor(doctor.getDoctorId(), doctorDto);

            assertNotNull(output);

            assertEquals(doctor.getName(), output.name());
            assertEquals(doctor.getGender(), output.gender());
            assertEquals(doctor.getDescription(), output.description());

            assertTrue(doctor.isActive());
        }

        @Test
        @DisplayName("Throws Exception when error occurs in trying update doctor")
        void ThrowsExceptionWhenErrorOccursInUpdateDoctor() {
            when(doctorRepository.findById(doctor.getDoctorId())).thenThrow(new DoctorNotFoundException("Not found"));
            when(doctorRepository.save(any(Doctor.class))).thenThrow(new RuntimeException());

            assertThrows(DoctorNotFoundException.class, () -> doctorService.updateDoctor(doctor.getDoctorId(), doctorDto));
            assertThrows(RuntimeException.class, () -> doctorService.createDoctor(doctorDto));
        }
    }

    @Nested
    class disableDoctor {
        @Test
        @DisplayName("Should disable doctor by id with success")
        void ShouldDisableDoctorWithSuccess() {
            long id = 1;

            when(doctorRepository.findById(id)).thenReturn(Optional.of(doctor));

            doctorService.disableDoctor(id);

            verify(doctorRepository).findById(id);
        }

        @Test
        @DisplayName("Throws exception when error occurs in method Disable Doctor by your id")
        void ThrowsExceptionWhenErrorOccursInDisableDoctorById() {
            long id = 1;

            doThrow(new DoctorNotFoundException("Not Found")).when(doctorRepository).findById(id);

            assertThrows(DoctorNotFoundException.class, () -> doctorService.disableDoctor(id));

            verify(doctorRepository).findById(id);
        }
    }
    public static Doctor createDoctorWithAppointments() {
        Doctor doctorTest = new Doctor (
                10,
                "Doctor",
                Speciality.CHILD_PSYCHOLOGY,
                'M',
                "1323455",
                "Experience with infantil psycholigy",
                null,
                true
        );

        Patient patient = new Patient(
                1,
                "Patient",
                19,
                'F',
                "Problemas",
                true
        );

        AppointmentScheduling appointment1 = new AppointmentScheduling(1L, doctorTest, patient, null, true);
        AppointmentScheduling appointment2 = new AppointmentScheduling(2L, doctorTest, patient, null, true);

        List<AppointmentScheduling> appointmentSchedulings = List.of(appointment1, appointment2);

        return new Doctor(
                1,
                "Doctor",
                Speciality.CHILD_PSYCHOLOGY,
                'M',
                "123456",
                "Experience with infantil psycholigy",
                appointmentSchedulings,
                true
        );
    }
    public static ResponseDoctorDto createDoctorDto() {
        Doctor doctorTest = new Doctor (
                10,
                "Doctor",
                Speciality.CHILD_PSYCHOLOGY,
                'M',
                "1323455",
                "Experience with infantil psycholigy",
                null,
                true
        );
        Patient patient = new Patient(
                1,
                "Patient",
                19,
                'F',
                "Problemas",
                true
        );
        AppointmentScheduling appointment1 = new AppointmentScheduling(1L, doctorTest, patient, null, true);
        AppointmentScheduling appointment2 = new AppointmentScheduling(2L, doctorTest, patient, null, true);

        List<AppointmentScheduling> appointmentSchedulings = List.of(appointment1, appointment2);

        return new ResponseDoctorDto(
                1L,
                "Doctor",
                Speciality.CHILD_PSYCHOLOGY,
                'F',
                "135453",
                "Experience with infantil psycholigy",
                appointmentSchedulings.stream().map(a -> new AppointmentResponseDto(
                        a.getSchedulingId(),
                        a.getPatient().getName(),
                        a.getDoctor().getName(),
                        a.getScheduledTimeFor(),
                        a.getActive())).toList(),
                true, doctor.getLinks());
    }
}