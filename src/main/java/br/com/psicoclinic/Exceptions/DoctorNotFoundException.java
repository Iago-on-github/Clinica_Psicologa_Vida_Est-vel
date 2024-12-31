package br.com.psicoclinic.Exceptions;

public class DoctorNotFoundException extends RuntimeException{
    public DoctorNotFoundException(String msg) {
        super(msg);
    }
}
