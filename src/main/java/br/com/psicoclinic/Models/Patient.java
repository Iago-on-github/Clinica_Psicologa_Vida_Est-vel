package br.com.psicoclinic.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tb_patient")
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long patient_id;
    private String name;
    private Integer age;
    private Character gender;
    private String description;
    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL)
    private List<AppointmentScheduling> appointments = new ArrayList<>();
    private boolean active;
    public Patient() {
    }

    public Patient(long patient_id, String name, Integer age, Character gender, String description, boolean active) {
        this.patient_id = patient_id;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.description = description;
        this.active = active;
    }

    public void disablePatiet(boolean active) {
        if (!active) this.active = false;
    }

    public long getPatient_id() {
        return patient_id;
    }

    public void setPatient_id(long patient_id) {
        this.patient_id = patient_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Character getGender() {
        return gender;
    }

    public void setGender(Character gender) {
        this.gender = gender;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @JsonIgnore
    public List<AppointmentScheduling> getAppointments() {
        return appointments;
    }

    public void setAppointments(List<AppointmentScheduling> appointments) {
        this.appointments = appointments;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
