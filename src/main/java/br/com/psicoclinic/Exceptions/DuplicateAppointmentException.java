package br.com.psicoclinic.Exceptions;

public class DuplicateAppointmentException extends RuntimeException{
    public DuplicateAppointmentException(String msg) {
        super(msg);
    }
}
