package br.com.psicoclinic.Resources;

import br.com.psicoclinic.Models.Dtos.ResponsePatientDto;
import br.com.psicoclinic.Models.Patient;
import br.com.psicoclinic.Service.PatientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/patients")
@Tag(name = "Patients", description = "Endpoint for Patients.")
public class PatientResources {
    private final PatientService patientService;
    @Autowired
    public PatientResources(PatientService patientService) {
        this.patientService = patientService;
    }

    @GetMapping
    @Operation(summary = "Get All Patients",
    description = "Get all active patients",
    tags = {"ResponsePatientDto"},
    responses = {@ApiResponse(description = "Success", responseCode = "200",
    content = @Content(schema = @Schema(implementation = ResponsePatientDto.class))
    ),
    @ApiResponse(description = "Bad Request", responseCode = "404", content = @Content),
    @ApiResponse(description = "No Content", responseCode = "204", content = @Content),
    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
    })
    public ResponseEntity<List<ResponsePatientDto>> getAllPatients() {
        return ResponseEntity.ok().body(patientService.getAllPatients());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get Patient By Id",
            description = "Get active Patient by id",
            tags = {"ResponsePatientDto"},
            responses = {@ApiResponse(description = "Success", responseCode = "200",
                    content = @Content(schema = @Schema(implementation = ResponsePatientDto.class))
            ),
                    @ApiResponse(description = "Bad Request", responseCode = "404", content = @Content),
                    @ApiResponse(description = "No Content", responseCode = "204", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
            })
    public ResponseEntity<ResponsePatientDto> getPatientById(@PathVariable Long id) {
        return ResponseEntity.ok().body(patientService.getPatientById(id));
    }

    @PostMapping
    @Operation(summary = "Create Patient",
            description = "Method for create Patient",
            tags = {"ResponsePatientDto"},
            responses = {@ApiResponse(description = "Created", responseCode = "201",
                    content = @Content(schema = @Schema(implementation = ResponsePatientDto.class))
            ),
                    @ApiResponse(description = "Bad Request", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
            })
    public ResponseEntity<ResponsePatientDto> createPatient(@RequestBody ResponsePatientDto dto, UriComponentsBuilder componentsBuilder) {
        var patient = patientService.createPatient(dto);
        URI uri = componentsBuilder.path("/{id}").buildAndExpand(patient.id()).toUri();
        return ResponseEntity.created(uri).body(patient);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update Patient",
            description = "Update active Patients by id",
            tags = {"ResponsePatientDto"},
            responses = {@ApiResponse(description = "Success", responseCode = "200",
                    content = @Content(schema = @Schema(implementation = ResponsePatientDto.class))
            ),
                    @ApiResponse(description = "Bad Request", responseCode = "404", content = @Content),
                    @ApiResponse(description = "No Content", responseCode = "204", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
            })
    public ResponseEntity<ResponsePatientDto> updatePatient(@PathVariable Long id, @RequestBody ResponsePatientDto dto) {
        return ResponseEntity.ok().body(patientService.updatePatient(id, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Inactive Patient",
            description = "Set Patient with inactive status",
            tags = {"ResponsePatientDto"},
            responses = {@ApiResponse(description = "noContent", responseCode = "204",
                    content = @Content(schema = @Schema(implementation = ResponsePatientDto.class))
            ),
                    @ApiResponse(description = "Bad Request", responseCode = "404", content = @Content),
                    @ApiResponse(description = "No Content", responseCode = "204", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
            })
    public ResponseEntity<Void> inactivePatient(@PathVariable Long id) {
        patientService.inactivePatient(id);
        return ResponseEntity.noContent().build();
    }
}
