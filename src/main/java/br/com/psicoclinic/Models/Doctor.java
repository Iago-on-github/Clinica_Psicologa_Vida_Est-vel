package br.com.psicoclinic.Models;

import br.com.psicoclinic.Models.Enumn.Speciality;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.springframework.hateoas.RepresentationModel;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tb_doctor")
public class Doctor extends RepresentationModel<Doctor> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long doctorId;
    private String name;
    @Enumerated(EnumType.STRING)
    private Speciality speciality;
    private Character gender;
    private String crm;
    private String description;
    @OneToMany(mappedBy = "doctor")
    private List<AppointmentScheduling> appointments = new ArrayList<>();
    private boolean active = true;
    public Doctor() {
    }

    public Doctor(long doctorId, String name, Speciality speciality, Character gender, String crm, String description, List<AppointmentScheduling> appointments, boolean active) {
        this.doctorId = doctorId;
        this.name = name;
        this.speciality = speciality;
        this.gender = gender;
        this.crm = crm;
        this.description = description;
        this.appointments = appointments;
        this.active = active;
    }

    public void disableDoctor(boolean active) {
        this.active = active;
    }

    public long getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(long doctorId) {
        this.doctorId = doctorId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Speciality getSpeciality() {
        return speciality;
    }

    public void setSpeciality(Speciality speciality) {
        this.speciality = speciality;
    }

    public Character getGender() {
        return gender;
    }

    public void setGender(Character gender) {
        this.gender = gender;
    }

    public String getCrm() {
        return crm;
    }

    public void setCrm(String crm) {
        this.crm = crm;
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
