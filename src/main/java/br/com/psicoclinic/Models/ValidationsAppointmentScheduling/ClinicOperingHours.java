package br.com.psicoclinic.Models.ValidationsAppointmentScheduling;


import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjusters;
public class ClinicOperingHours {
    public void clinicOperation() {
        LocalDateTime openOperationHour = LocalDateTime.now().with(LocalTime.of(7, 0));
        LocalDateTime closeOperationHour = LocalDateTime.now().with(LocalTime.of(19, 0));
        LocalDate sunday = LocalDate.now().with(TemporalAdjusters.next(DayOfWeek.SUNDAY));

        Boolean isBeforeOpening = LocalDateTime.now().toLocalTime().isBefore(openOperationHour.toLocalTime());
        Boolean isAfterClosing = LocalDateTime.now().toLocalTime().isAfter(closeOperationHour.toLocalTime());
        Boolean isSuday = LocalDateTime.now().getDayOfWeek() == DayOfWeek.SUNDAY;

        if (isBeforeOpening || isAfterClosing || isSuday) {
            System.out.println("Psycho-clinic is only open from 7am to 7pm, Mon. on Sat.");
        }
    }
}
