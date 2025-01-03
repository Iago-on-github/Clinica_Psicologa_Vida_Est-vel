package br.com.psicoclinic.Resources;

import br.com.psicoclinic.Models.Doctor;
import br.com.psicoclinic.Models.Dtos.ResponseDoctorDto;
import br.com.psicoclinic.Models.Dtos.ResponseDoctorDtoWithHateoasLinks;
import br.com.psicoclinic.Models.Dtos.ResponsePatientDto;
import br.com.psicoclinic.Models.Enumn.Speciality;
import br.com.psicoclinic.Service.DoctorService;
import br.com.psicoclinic.util.mediaType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/doctor")
@Tag(name = "Doctor", description = "Endpoint for Doctors")
public class DoctorResources {
    private final DoctorService doctorService;
    @Autowired
    public DoctorResources(DoctorService doctorService) {
        this.doctorService = doctorService;
    }
    @GetMapping(produces = {
            mediaType.APPLICATION_JSON,
            mediaType.APPLICATION_XML,
            mediaType.APPLICATION_YAML
    })
    @Operation(summary = "Get All Doctors",
            description = "Get all active Doctors",
            tags = {"ResponseDoctorDtoWithHateoasLinks"},
            responses = {@ApiResponse(description = "Success", responseCode = "200",
                    content = @Content(schema = @Schema(implementation = ResponseDoctorDtoWithHateoasLinks.class))
            ),
                    @ApiResponse(description = "Bad Request", responseCode = "404", content = @Content),
                    @ApiResponse(description = "No Content", responseCode = "204", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
            })
    public ResponseEntity<PagedModel<EntityModel<ResponseDoctorDtoWithHateoasLinks>>> getAllDoctors(
                                                                 @RequestParam(value = "page", defaultValue = "0") Integer page,
                                                                 @RequestParam(value = "limit", defaultValue = "12") Integer limit,
                                                                 @RequestParam(value = "direction", defaultValue = "asc") String direction) {

        var sortDirection = "desc".equalsIgnoreCase(direction) ? Sort.Direction.DESC : Sort.Direction.ASC;

        Pageable pageable = PageRequest.of(page, limit, Sort.by(sortDirection, "name"));

        return ResponseEntity.ok().body(doctorService.getAllDoctors(pageable));
    }

    @GetMapping(value = "/{id}", produces = {
            mediaType.APPLICATION_JSON,
            mediaType.APPLICATION_XML,
            mediaType.APPLICATION_YAML
    })
    @Operation(summary = "Get Doctor by id",
            description = "Get active Doctor by id",
            tags = {"ResponseDoctorDto"},
            responses = {@ApiResponse(description = "Success", responseCode = "200",
                    content = @Content(schema = @Schema(implementation = ResponseDoctorDto.class))
            ),
                    @ApiResponse(description = "Bad Request", responseCode = "404", content = @Content),
                    @ApiResponse(description = "No Content", responseCode = "204", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
            })
    public ResponseEntity<ResponseDoctorDto> getDoctorById(@PathVariable Long id) {
        return ResponseEntity.ok().body(doctorService.getDoctorById(id));
    }

    @GetMapping(value = "/speciality/", produces = {
            mediaType.APPLICATION_JSON,
            mediaType.APPLICATION_XML,
            mediaType.APPLICATION_YAML
    })
    @Operation(summary = "Get Doctor by id",
            description = "Get active Doctor by id",
            tags = {"ResponseDoctorDtoWithHateoasLinks"},
            responses = {@ApiResponse(description = "Success", responseCode = "200",
                    content = @Content(schema = @Schema(implementation = ResponseDoctorDtoWithHateoasLinks.class))
            ),
                    @ApiResponse(description = "Bad Request", responseCode = "404", content = @Content),
                    @ApiResponse(description = "No Content", responseCode = "204", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
            })
    public ResponseEntity<List<ResponseDoctorDtoWithHateoasLinks>> getDoctorBySpeciality(@RequestParam Speciality speciality) {
        return ResponseEntity.ok().body(doctorService.getDoctorBySpeciality(speciality));
    }

    @PostMapping
    @Operation(summary = "Create Doctor",
            description = "Method for Create Doctor",
            tags = {"ResponseDoctorDto"},
            responses = {@ApiResponse(description = "Created", responseCode = "201",
                    content = @Content(schema = @Schema(implementation = ResponseDoctorDto.class))
            ),
                    @ApiResponse(description = "Bad Request", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
            })
    public ResponseEntity<ResponseDoctorDto> createDoctor(@RequestBody ResponseDoctorDto dto, UriComponentsBuilder componentsBuilder) {
        var doctor = doctorService.createDoctor(dto);
        URI uri = componentsBuilder.path("/{id}").buildAndExpand(doctor.id()).toUri();
        return ResponseEntity.created(uri).body(doctor);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update Doctor",
            description = "Update active doctor by id",
            tags = {"ResponseDoctorDto"},
            responses = {@ApiResponse(description = "Success", responseCode = "200",
                    content = @Content(schema = @Schema(implementation = Doctor.class))
            ),
                    @ApiResponse(description = "Bad Request", responseCode = "404", content = @Content),
                    @ApiResponse(description = "No Content", responseCode = "204", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
            })
    public ResponseEntity<ResponseDoctorDto> updateDoctor(@PathVariable Long id, @RequestBody ResponseDoctorDto dto) {
        return ResponseEntity.ok().body(doctorService.updateDoctor(id, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Inactive Doctor",
            description = "Set Doctor with inactive Status.",
            tags = {"ResponseDoctorDto"},
            responses = {@ApiResponse(description = "NoContent", responseCode = "204",
                    content = @Content(schema = @Schema(implementation = ResponseDoctorDto.class))
            ),
                    @ApiResponse(description = "Bad Request", responseCode = "404", content = @Content),
                    @ApiResponse(description = "No Content", responseCode = "204", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
            })
    public ResponseEntity<Void> disableDoctor(@PathVariable Long id) {
        doctorService.disableDoctor(id);
        return ResponseEntity.noContent().build();
    }
}
