package br.com.psicoclinic.Service;

import br.com.psicoclinic.Exceptions.PatientNotFoundException;
import br.com.psicoclinic.Models.Dtos.AppointmentResponseDto;
import br.com.psicoclinic.Models.Dtos.AppointmentSchedulingDto;
import br.com.psicoclinic.Models.Dtos.ResponsePatientDto;
import br.com.psicoclinic.Models.Patient;
import br.com.psicoclinic.Repositories.PatientRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PatientService {
    private final PatientRepository patientRepository;
    @Autowired
    public PatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    public Page<ResponsePatientDto> getAllPatients(Pageable pageable) {
        var patient = patientRepository.findAll(pageable);

        return patient.map(p -> new ResponsePatientDto(
                        p.getPatient_id(),
                        p.getName(),
                        p.getAge(),
                        p.getGender(),
                        p.getDescription(),
                        p.getAppointments()
                                .stream().map(a -> new AppointmentResponseDto(
                                        a.getSchedulingId(),
                                        a.getPatient().getName(),
                                        a.getDoctor().getName(),
                                        a.getScheduledTimeFor(),
                                        a.getActive())).toList(),
                        p.isActive()));
    }

    public List<ResponsePatientDto> getAllActivePatients() {
        var patient = patientRepository.findByActiveTrue();

        return patient.stream()
                .map(p -> new ResponsePatientDto(
                        p.getPatient_id(),
                        p.getName(),
                        p.getAge(),
                        p.getGender(),
                        p.getDescription(),
                        p.getAppointments()
                                .stream().map(a -> new AppointmentResponseDto(
                                        a.getSchedulingId(),
                                        a.getPatient().getName(),
                                        a.getDoctor().getName(),
                                        a.getScheduledTimeFor(),
                                        a.getActive())).toList(),
                        p.isActive())).toList();
    }

    public ResponsePatientDto getPatientById(Long id) {
        var patient = patientRepository.findById(id).orElseThrow(() -> new PatientNotFoundException("Patient not found"));

        return new ResponsePatientDto(
                patient.getPatient_id(),
                patient.getName(),
                patient.getAge(),
                patient.getGender(),
                patient.getDescription(),
                appointmentResponseDto(id),
                patient.isActive()
        );
    }

    @Transactional
    public ResponsePatientDto createPatient(ResponsePatientDto dto) {
        Patient patient = new Patient();

        patient.setName(dto.name());
        patient.setAge(dto.age());
        patient.setGender(dto.gender());
        patient.setDescription(dto.description());
        patient.setActive(true);

        patientRepository.save(patient);

        return new ResponsePatientDto(
                patient.getPatient_id(),
                patient.getName(),
                patient.getAge(),
                patient.getGender(),
                patient.getDescription(),
                appointmentResponseDto(dto.id()),
                patient.isActive());
    }

    @Transactional
    public ResponsePatientDto updatePatient(Long id, ResponsePatientDto dto) {
        var patient = patientRepository.findById(id).orElseThrow(() -> new PatientNotFoundException("Patient not found"));

        patient.setAge(dto.age());
        patient.setDescription(dto.description());
        patient.setActive(dto.patientActive());

        patientRepository.save(patient);

        return new ResponsePatientDto(
                patient.getPatient_id(),
                patient.getName(),
                patient.getAge(),
                patient.getGender(),
                patient.getDescription(),
                appointmentResponseDto(id),
                patient.isActive());
    }

    @Transactional
    public void inactivePatient(Long id) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new PatientNotFoundException("Patient not found"));
        patient.disablePatiet(false);
        patientRepository.save(patient);
    }

    private List<AppointmentResponseDto> appointmentResponseDto(long id){
        var patient = patientRepository.findById(id)
                .orElseThrow(() -> new PatientNotFoundException("Patient not found."));

        return patient.getAppointments()
                .stream()
                .map(app -> new AppointmentResponseDto(
                        app.getSchedulingId(),
                        app.getPatient().getName(),
                        app.getDoctor().getName(),
                        app.getScheduledTimeFor(),
                        app.getActive()
                ))
                .toList();
    }
}
