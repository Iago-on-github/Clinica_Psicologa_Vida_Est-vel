package br.com.psicoclinic.Repositories;

import br.com.psicoclinic.Models.AppointmentScheduling;
import br.com.psicoclinic.Models.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AppointmentSchedulingRepository extends JpaRepository<AppointmentScheduling, Long> {
    List<AppointmentScheduling> findByActiveAndScheduledTimeForBefore(boolean active, LocalDateTime localDateTime);

    List<AppointmentScheduling> findByPatient(Patient patient);
    @Query("SELECT a FROM AppointmentScheduling a WHERE a.patient = :patient AND a.active = true AND a.scheduledTimeFor > :currentTime")
    List<AppointmentScheduling> findActiveAppointmentsByPatient(@Param("patient") Patient patient, @Param("currentTime") LocalDateTime currentTime);}
