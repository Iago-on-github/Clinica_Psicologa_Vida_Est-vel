package br.com.psicoclinic.Resources;

import br.com.psicoclinic.Models.Dtos.ResponsePatientDto;
import br.com.psicoclinic.Service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/patients")
public class PatientResources {
    private final PatientService patientService;
    @Autowired
    public PatientResources(PatientService patientService) {
        this.patientService = patientService;
    }

    @GetMapping
    public ResponseEntity<List<ResponsePatientDto>> getAllPatients() {
        return ResponseEntity.ok().body(patientService.getAllPatients());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponsePatientDto> getPatientById(@PathVariable Long id) {
        return ResponseEntity.ok().body(patientService.getPatientById(id));
    }

    @PostMapping
    public ResponseEntity<ResponsePatientDto> createPatient(@RequestBody ResponsePatientDto dto, UriComponentsBuilder componentsBuilder) {
        var patient = patientService.createPatient(dto);
        URI uri = componentsBuilder.path("/{id}").buildAndExpand(patient.id()).toUri();
        return ResponseEntity.created(uri).body(patient);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponsePatientDto> updatePatient(@PathVariable Long id, @RequestBody ResponsePatientDto dto) {
        return ResponseEntity.ok().body(patientService.updatePatient(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> inactivePatient(@PathVariable Long id) {
        patientService.inactivePatient(id);
        return ResponseEntity.noContent().build();
    }
}
