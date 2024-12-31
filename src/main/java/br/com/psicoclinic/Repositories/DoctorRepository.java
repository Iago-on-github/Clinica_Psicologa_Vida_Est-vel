package br.com.psicoclinic.Repositories;

import br.com.psicoclinic.Models.Doctor;
import br.com.psicoclinic.Models.Enumn.Speciality;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long> {

    @Query("SELECT d FROM Doctor d WHERE d.id NOT IN " +
            "(SELECT a.doctor.id FROM AppointmentScheduling a WHERE a.scheduledTimeFor = :scheduledTimeFor) " +
            "ORDER BY FUNCTION('RANDOM') LIMIT 1")
    Doctor findByChooseAFreeRandomDoctor(@Param("scheduledTimeFor") LocalDateTime scheduledTimeFor);

    @Query("SELECT d FROM Doctor d WHERE d.speciality = :speciality")
    List<Doctor> findDoctorBySpeciality(@Param("speciality") Speciality speciality);
}
