package br.com.psicoclinic.Service;

import br.com.psicoclinic.Exceptions.DoctorNotFoundException;
import br.com.psicoclinic.Exceptions.DuplicateAppointmentException;
import br.com.psicoclinic.Exceptions.PatientNotFoundException;
import br.com.psicoclinic.Models.AppointmentHistory;
import br.com.psicoclinic.Models.AppointmentScheduling;
import br.com.psicoclinic.Models.Dtos.AppointmentSchedulingDto;
import br.com.psicoclinic.Models.Patient;
import br.com.psicoclinic.Repositories.AppointmentHistoryRepository;
import br.com.psicoclinic.Repositories.AppointmentSchedulingRepository;
import br.com.psicoclinic.Repositories.DoctorRepository;
import br.com.psicoclinic.Repositories.PatientRepository;
import jakarta.transaction.Transactional;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class AppointmentSchedulingService {
    private final AppointmentSchedulingRepository repository;
    private final AppointmentHistoryRepository appointmentHistoryRepository;
    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;

    public AppointmentSchedulingService(AppointmentSchedulingRepository repository, AppointmentHistoryRepository appointmentHistoryRepository, PatientRepository patientRepository, DoctorRepository doctorRepository) {
        this.repository = repository;
        this.appointmentHistoryRepository = appointmentHistoryRepository;
        this.patientRepository = patientRepository;
        this.doctorRepository = doctorRepository;
    }

    public AppointmentSchedulingDto create(AppointmentSchedulingDto dto) {
        AppointmentScheduling appointment = new AppointmentScheduling();

        Patient patient = patientRepository.findById(dto.patientId())
                .orElseThrow(() -> new PatientNotFoundException("Patient not found"));
        if (!patient.isActive()) throw new PatientNotFoundException("Sorry. This patient " + patient.getName() + " doesn't active.");

        var doctor = doctorRepository.findByChooseAFreeRandomDoctor(dto.scheduledTimeFor());
        if (doctor == null) throw new DoctorNotFoundException("Doctor not found.");

        if (hasAppointmentToday(patient, dto)) throw new DuplicateAppointmentException("This patient already has an appointment today.");

        appointment.setDoctor(doctor);
        appointment.setPatient(patient);
        appointment.setScheduledTimeFor(dto.scheduledTimeFor());

        repository.save(appointment);

        return new AppointmentSchedulingDto(
                appointment.getSchedulingId(),
                appointment.getPatient().getPatient_id(),
                appointment.getDoctor().getDoctorId(),
                appointment.getScheduledTimeFor(),
                appointment.getActive());
    }
    public List<AppointmentSchedulingDto> getActiveAppointmentsByPatient(Long id) {
        var patient = patientRepository.findById(id).orElseThrow(() -> new PatientNotFoundException("Patient not found"));

        var activeAppointments = repository.findActiveAppointmentsByPatient(patient, LocalDateTime.now());

        return activeAppointments
                .stream()
                .map(p -> new AppointmentSchedulingDto(
                        p.getSchedulingId(),
                        p.getDoctor().getDoctorId(),
                        p.getPatient().getPatient_id(),
                        p.getScheduledTimeFor(),
                        p.getActive())).toList();
    }
    private boolean hasAppointmentToday(Patient patient, AppointmentSchedulingDto dto) {
        return patient.getAppointments()
                .stream()
                .anyMatch(a -> a.getScheduledTimeFor().toLocalDate().equals(dto.scheduledTimeFor().toLocalDate()));
    }
}
