package br.com.psicoclinic.Resources;

import br.com.psicoclinic.Models.AppointmentScheduling;
import br.com.psicoclinic.Models.Dtos.AppointmentSchedulingDto;
import br.com.psicoclinic.Models.Dtos.ResponseDoctorDto;
import br.com.psicoclinic.Models.Dtos.ResponsePatientDto;
import br.com.psicoclinic.Service.AppointmentSchedulingService;
import br.com.psicoclinic.util.mediaType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/appointment")
public class AppointmentSchedulingResources {
    private final AppointmentSchedulingService appointmentSchedulingService;

    public AppointmentSchedulingResources(AppointmentSchedulingService appointmentSchedulingService) {
        this.appointmentSchedulingService = appointmentSchedulingService;
    }

    @PostMapping
    @Operation(summary = "Create Appointment",
            description = "Method for Create AppointmentScheduling",
            tags = {"AppointmentSchedulingDto"},
            responses = {@ApiResponse(description = "Created", responseCode = "201",
                    content = @Content(schema = @Schema(implementation = AppointmentSchedulingDto.class))
            ),
                    @ApiResponse(description = "Bad Request", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
            })
    public ResponseEntity<AppointmentSchedulingDto> create(@RequestBody AppointmentSchedulingDto dto, UriComponentsBuilder componentsBuilder) {
        var appointment = new AppointmentScheduling();
        URI uri = componentsBuilder.path("/{id}").buildAndExpand(appointment.getSchedulingId()).toUri();
        return ResponseEntity.created(uri).body(appointmentSchedulingService.create(dto));
    }

    @GetMapping(value = "/{id}", produces = {
            mediaType.APPLICATION_JSON,
            mediaType.APPLICATION_XML,
            mediaType.APPLICATION_YAML
    })
    @Operation(summary = "Get Active Appointments",
            description = "Get active appointments by patient id",
            tags = {"AppointmentSchedulingDto"},
            responses = {@ApiResponse(description = "Success", responseCode = "200",
                    content = @Content(schema = @Schema(implementation = AppointmentSchedulingDto.class))
            ),
                    @ApiResponse(description = "Bad Request", responseCode = "404", content = @Content),
                    @ApiResponse(description = "No Content", responseCode = "204", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
            })
    public ResponseEntity<List<AppointmentSchedulingDto>> getActiveAppointmentsByPatient(@PathVariable Long id) {
        return ResponseEntity.ok().body(appointmentSchedulingService.getActiveAppointmentsByPatient(id));
    }
}
