package br.com.psicoclinic.Resources;

import br.com.psicoclinic.Models.AppointmentScheduling;
import br.com.psicoclinic.Models.Dtos.AppointmentSchedulingDto;
import br.com.psicoclinic.Service.AppointmentSchedulingService;
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
    public ResponseEntity<AppointmentSchedulingDto> create(@RequestBody AppointmentSchedulingDto dto, UriComponentsBuilder componentsBuilder) {
        var appointment = new AppointmentScheduling();
        URI uri = componentsBuilder.path("/{id}").buildAndExpand(appointment.getSchedulingId()).toUri();
        return ResponseEntity.created(uri).body(appointmentSchedulingService.create(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<AppointmentSchedulingDto>> getActiveAppointmentsByPatient(@PathVariable Long id) {
        return ResponseEntity.ok().body(appointmentSchedulingService.getActiveAppointmentsByPatient(id));
    }
}
