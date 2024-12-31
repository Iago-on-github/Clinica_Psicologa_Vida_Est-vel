package br.com.psicoclinic.Resources;

import br.com.psicoclinic.Models.AppointmentHistory;
import br.com.psicoclinic.Models.Dtos.AppointmentHistoryDto;
import br.com.psicoclinic.Service.AppointmentHistoryService;
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
    public ResponseEntity<List<AppointmentHistoryDto>> getAppointmentHistoryByPatientId(@PathVariable Long id) {
        return ResponseEntity.ok().body(appointmentHistoryService.getHistoryByPatientId(id));
    }
}
