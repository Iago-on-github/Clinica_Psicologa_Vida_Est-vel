package br.com.psicoclinic.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "tb_appointment_history")
public class AppointmentHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long historyId;
    private LocalDateTime scheduledTimeFor;
    private LocalDateTime endTime;
    @ManyToOne
    @JoinColumn(name = "doctor_id")
    private Doctor doctor;
    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;
    private String description;

    public AppointmentHistory() {
    }

    public AppointmentHistory(long historyId, LocalDateTime scheduledTimeFor, LocalDateTime endTime, Doctor doctor, Patient patient, String description) {
        this.historyId = historyId;
        this.scheduledTimeFor = scheduledTimeFor;
        this.endTime = endTime;
        this.doctor = doctor;
        this.patient = patient;
        this.description = description;
    }

    public long getHistoryId() {
        return historyId;
    }

    public void setHistoryId(long historyId) {
        this.historyId = historyId;
    }

    public LocalDateTime getScheduledTimeFor() {
        return scheduledTimeFor;
    }

    public void setScheduledTimeFor(LocalDateTime scheduledTimeFor) {
        this.scheduledTimeFor = scheduledTimeFor;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
