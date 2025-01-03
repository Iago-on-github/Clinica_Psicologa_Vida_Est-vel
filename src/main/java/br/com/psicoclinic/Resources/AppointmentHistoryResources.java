package br.com.psicoclinic.Resources;

import br.com.psicoclinic.Models.AppointmentHistory;
import br.com.psicoclinic.Models.Dtos.AppointmentHistoryDto;
import br.com.psicoclinic.Models.Dtos.AppointmentSchedulingDto;
import br.com.psicoclinic.Service.AppointmentHistoryService;
import br.com.psicoclinic.util.mediaType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/history")
public class AppointmentHistoryResources {
    private final AppointmentHistoryService appointmentHistoryService;

    public AppointmentHistoryResources(AppointmentHistoryService appointmentHistoryService) {
        this.appointmentHistoryService = appointmentHistoryService;
    }

    @GetMapping(value = "/{id}", produces = {
            mediaType.APPLICATION_JSON,
            mediaType.APPLICATION_XML,
            mediaType.APPLICATION_YAML
    })
    @Operation(summary = "Get Appointment History",
            description = "Get Appointment History By Patient Id",
            tags = {"AppointmentHistoryDto"},
            responses = {@ApiResponse(description = "Success", responseCode = "200",
                    content = @Content(schema = @Schema(implementation = AppointmentHistoryDto.class))
            ),
                    @ApiResponse(description = "Bad Request", responseCode = "404", content = @Content),
                    @ApiResponse(description = "No Content", responseCode = "204", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
            })
    public ResponseEntity<Page<AppointmentHistoryDto>> getAppointmentHistoryByPatientId(@RequestParam(value = "page", defaultValue = "0") Integer page,
                                                                                        @RequestParam(value = "limit", defaultValue = "5") Integer limit,
                                                                                        @RequestParam(value = "description", defaultValue = "asc") String description,
                                                                                        @PathVariable Long id) {

        var sortDirection = "desc".equalsIgnoreCase(description) ? Sort.Direction.DESC : Sort.Direction.ASC;

        Pageable pageable = PageRequest.of(page, limit, Sort.by(sortDirection, "historyId"));

        return ResponseEntity.ok().body(appointmentHistoryService.getHistoryByPatientId(id, pageable));
    }
}
