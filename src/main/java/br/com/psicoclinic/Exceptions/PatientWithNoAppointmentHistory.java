package br.com.psicoclinic.Exceptions;

public class PatientWithNoAppointmentHistory extends RuntimeException {
    public PatientWithNoAppointmentHistory(String msg) {
        super(msg);
    }
}
