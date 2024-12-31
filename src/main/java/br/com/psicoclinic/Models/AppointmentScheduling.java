package br.com.psicoclinic.Models;

import jakarta.persistence.*;

import java.time.LocalDateTime;
@Entity
@Table(name = "tb_appointmentScheduling")
public class AppointmentScheduling {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long schedulingId;
    @ManyToOne
    @JoinColumn(name = "doctor_id", nullable = false)
    private Doctor doctor;
    @ManyToOne
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;
    @Column(name = "scheduledtimefor", nullable = false)
    private LocalDateTime scheduledTimeFor;
    private Boolean active = true;

    public AppointmentScheduling() {}

    public AppointmentScheduling(long schedulingId, Doctor doctor, Patient patient, LocalDateTime scheduledTimeFor, Boolean active) {
        this.schedulingId = schedulingId;
        this.doctor = doctor;
        this.patient = patient;
        this.scheduledTimeFor = scheduledTimeFor;
        this.active = active;
    }

    public long getSchedulingId() {
        return schedulingId;
    }

    public void setSchedulingId(long schedulingId) {
        this.schedulingId = schedulingId;
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

    public LocalDateTime getScheduledTimeFor() {
        return scheduledTimeFor;
    }

    public void setScheduledTimeFor(LocalDateTime scheduledTimeFor) {
        this.scheduledTimeFor = scheduledTimeFor;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}
