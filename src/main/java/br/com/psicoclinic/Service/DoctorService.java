package br.com.psicoclinic.Service;

import br.com.psicoclinic.Exceptions.DoctorNotFoundException;
import br.com.psicoclinic.Models.Doctor;
import br.com.psicoclinic.Models.Dtos.AppointmentResponseDto;
import br.com.psicoclinic.Models.Dtos.ResponseDoctorDto;
import br.com.psicoclinic.Models.Dtos.ResponseDoctorDtoWithHateoasLinks;
import br.com.psicoclinic.Models.Enumn.Speciality;
import br.com.psicoclinic.Repositories.DoctorRepository;
import br.com.psicoclinic.Resources.DoctorResources;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Links;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class DoctorService {
    private final DoctorRepository doctorRepository;
    private final PagedResourcesAssembler<ResponseDoctorDtoWithHateoasLinks> assembler;
    @Autowired
    public DoctorService(DoctorRepository doctorsRepository, PagedResourcesAssembler<ResponseDoctorDtoWithHateoasLinks> assembler) {
        this.doctorRepository = doctorsRepository;
        this.assembler = assembler;
    }

    public PagedModel<EntityModel<ResponseDoctorDtoWithHateoasLinks>> getAllDoctors(Pageable pageable) {
        Page<Doctor> doctors = doctorRepository.findAll(pageable);

        Page<ResponseDoctorDtoWithHateoasLinks> doctorDto = doctors
                .map(d -> new ResponseDoctorDtoWithHateoasLinks(
                        d.getDoctorId(),
                        d.getName(),
                        d.getSpeciality(),
                        d.getGender(),
                        d.getCrm(),
                        d.getDescription(),
                        d.getAppointments().stream().map(a -> new AppointmentResponseDto(
                                a.getSchedulingId(),
                                a.getPatient().getName(),
                                a.getDoctor().getName(),
                                a.getScheduledTimeFor(),
                                a.getActive()
                        )).toList(),
                        d.isActive(),
                        Links.of(linkTo(methodOn(DoctorResources.class)
                                .getDoctorById(d.getDoctorId())).withSelfRel())));

        return assembler.toModel(doctorDto);
    }

    public ResponseDoctorDto getDoctorById(Long id) {
        var doctor = doctorRepository.findById(id)
                .orElseThrow(() -> new DoctorNotFoundException("Doctor Not Found."));

        return new ResponseDoctorDto(
                doctor.getDoctorId(),
                doctor.getName(),
                doctor.getSpeciality(),
                doctor.getGender(),
                doctor.getCrm(),
                doctor.getDescription(),
                appointmentResponse(doctor.getDoctorId()),
                doctor.isActive());
    }

    public List<ResponseDoctorDtoWithHateoasLinks> getDoctorBySpeciality(Speciality speciality) {
       var doctors = doctorRepository.findDoctorBySpeciality(speciality);

       if (doctors.isEmpty()) throw new DoctorNotFoundException("Not Found");

        doctors.forEach(doctor -> doctor.add(linkTo(methodOn(DoctorResources.class)
                .getDoctorById(doctor.getDoctorId())).withSelfRel()));

        return doctors.stream().map(doctor -> new ResponseDoctorDtoWithHateoasLinks(
                doctor.getDoctorId(),
                doctor.getName(),
                doctor.getSpeciality(),
                doctor.getGender(),
                doctor.getCrm(),
                doctor.getDescription(),
                appointmentResponse(doctor.getDoctorId()),
                doctor.isActive(),
                doctor.getLinks()
        )).toList();
    }

    @Transactional
    public ResponseDoctorDto createDoctor(ResponseDoctorDto dto) {
        Doctor doctor = new Doctor();

        doctor.setName(dto.name());
        doctor.setSpeciality(dto.speciality());
        doctor.setGender(dto.gender());
        doctor.setCrm(dto.crm());
        doctor.setDescription(dto.description());

        doctorRepository.save(doctor);

        return new ResponseDoctorDto(
                doctor.getDoctorId(),
                doctor.getName(),
                doctor.getSpeciality(),
                doctor.getGender(),
                doctor.getCrm(),
                doctor.getDescription(),
                appointmentResponse(doctor.getDoctorId()),
                doctor.isActive());
    }

    @Transactional
    public ResponseDoctorDto updateDoctor(Long id, ResponseDoctorDto dto) {
        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() -> new DoctorNotFoundException("Doctor Not Found."));

        doctor.setName(dto.name());
        doctor.setGender(dto.gender());
        doctor.setDescription(dto.description());

        doctorRepository.save(doctor);

        return new ResponseDoctorDto(
                doctor.getDoctorId(),
                doctor.getName(),
                doctor.getSpeciality(),
                doctor.getGender(),
                doctor.getCrm(),
                doctor.getDescription(),
                appointmentResponse(doctor.getDoctorId()),
                doctor.isActive());
    }

    @Transactional
    public void disableDoctor(Long id) {
        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() -> new DoctorNotFoundException("Doctor Not Found."));
        doctor.disableDoctor(false);
        doctorRepository.save(doctor);
    }

    private List<AppointmentResponseDto> appointmentResponse(long id) {
        var doctor = doctorRepository.findById(id)
                .orElseThrow(() -> new DoctorNotFoundException("Doctor not found."));

        return doctor
                .getAppointments()
                .stream().map(d -> new AppointmentResponseDto(
                        d.getSchedulingId(),
                        d.getPatient().getName(),
                        d.getDoctor().getName(),
                        d.getScheduledTimeFor(),
                        d.getActive())).toList();
    }
}
