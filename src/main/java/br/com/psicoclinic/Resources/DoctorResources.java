package br.com.psicoclinic.Resources;

import br.com.psicoclinic.Models.Dtos.ResponseDoctorDto;
import br.com.psicoclinic.Models.Dtos.ResponseDoctorDtoWithHateoasLinks;
import br.com.psicoclinic.Models.Enumn.Speciality;
import br.com.psicoclinic.Service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/doctor")
public class DoctorResources {
    private final DoctorService doctorService;
    @Autowired
    public DoctorResources(DoctorService doctorService) {
        this.doctorService = doctorService;
    }
    @GetMapping
    public ResponseEntity<List<ResponseDoctorDtoWithHateoasLinks>> getAllDoctors() {
        return ResponseEntity.ok().body(doctorService.getAllDoctors());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDoctorDto> getDoctorById(@PathVariable Long id) {
        return ResponseEntity.ok().body(doctorService.getDoctorById(id));
    }

    @GetMapping("/speciality/")
    public ResponseEntity<List<ResponseDoctorDtoWithHateoasLinks>> getDoctorBySpeciality(@RequestParam Speciality speciality) {
        return ResponseEntity.ok().body(doctorService.getDoctorBySpeciality(speciality));
    }

    @PostMapping
    public ResponseEntity<ResponseDoctorDto> createDoctor(@RequestBody ResponseDoctorDto dto, UriComponentsBuilder componentsBuilder) {
        var doctor = doctorService.createDoctor(dto);
        URI uri = componentsBuilder.path("/{id}").buildAndExpand(doctor.id()).toUri();
        return ResponseEntity.created(uri).body(doctor);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseDoctorDto> updateDoctor(@PathVariable Long id, @RequestBody ResponseDoctorDto dto) {
        return ResponseEntity.ok().body(doctorService.updateDoctor(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> disableDoctor(@PathVariable Long id) {
        doctorService.disableDoctor(id);
        return ResponseEntity.noContent().build();
    }
}
