package br.com.psicoclinic.Repositories;

import br.com.psicoclinic.Models.AppointmentHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppointmentHistoryRepository extends JpaRepository<AppointmentHistory, Long> {
    @Query("SELECT a FROM AppointmentHistory a WHERE a.patient.id = :id")
    List<AppointmentHistory> findByPatientId (@Param("id") Long id);
}
