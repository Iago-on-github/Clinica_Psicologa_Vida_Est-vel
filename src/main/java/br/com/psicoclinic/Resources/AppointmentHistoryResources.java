package br.com.psicoclinic.Resources;

import br.com.psicoclinic.Models.AppointmentHistory;
import br.com.psicoclinic.Models.Dtos.AppointmentHistoryDto;
import br.com.psicoclinic.Models.Dtos.AppointmentSchedulingDto;
import br.com.psicoclinic.Service.AppointmentHistoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/history")
public class AppointmentHistoryResources {
    private final AppointmentHistoryService appointmentHistoryService;

    public AppointmentHistoryResources(AppointmentHistoryService appointmentHistoryService) {
        this.appointmentHistoryService = appointmentHistoryService;
    }

    @GetMapping("/{id}")
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
    public ResponseEntity<List<AppointmentHistoryDto>> getAppointmentHistoryByPatientId(@PathVariable Long id) {
        return ResponseEntity.ok().body(appointmentHistoryService.getHistoryByPatientId(id));
    }
}
