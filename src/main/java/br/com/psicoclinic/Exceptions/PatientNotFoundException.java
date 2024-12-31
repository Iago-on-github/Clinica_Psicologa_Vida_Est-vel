package br.com.psicoclinic.Exceptions;

public class PatientNotFoundException extends RuntimeException{
    public PatientNotFoundException(String msg) {
        super(msg);
    }
}
