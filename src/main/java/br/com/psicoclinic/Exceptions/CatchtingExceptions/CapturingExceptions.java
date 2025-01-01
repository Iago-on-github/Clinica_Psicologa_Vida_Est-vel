package br.com.psicoclinic.Exceptions.CatchtingExceptions;

import br.com.psicoclinic.Exceptions.*;
import br.com.psicoclinic.Exceptions.StandardError.StandardError;
import com.auth0.jwt.exceptions.TokenExpiredException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class CapturingExceptions {
    @ExceptionHandler(PatientNotFoundException.class)
    public ResponseEntity<StandardError> patientNotFound(PatientNotFoundException e, HttpServletRequest http) {
        new StandardError(LocalDateTime.now(), HttpStatus.NOT_FOUND.value(), "Patient Not Found.", http.getRequestURI());
        return ResponseEntity.notFound().build();
    }
    @ExceptionHandler(DoctorNotFoundException.class)
    public ResponseEntity<StandardError> doctorNotFound(DoctorNotFoundException e, HttpServletRequest http) {
        new StandardError(LocalDateTime.now(), HttpStatus.NOT_FOUND.value(), "Doctor Not Found.", http.getRequestURI());
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(PatientWithNoAppointmentHistory.class)
    public ResponseEntity<StandardError> patientWithNoAppointmentHistory(PatientWithNoAppointmentHistory e, HttpServletRequest http) {
        new StandardError(LocalDateTime.now(), HttpStatus.NOT_FOUND.value(), "Patient With No Appointment History.", http.getRequestURI());
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(DuplicateAppointmentException.class)
    public ResponseEntity<StandardError> duplicateAppointment(DuplicateAppointmentException e, HttpServletRequest http) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new StandardError(
                LocalDateTime.now(),
                HttpStatus.CONFLICT.value(),
                "Duplicate Appointment In This Patient.",
                http.getRequestURI()));
    }

    @ExceptionHandler(InvalidJwtAuthenticationToken.class)
    public ResponseEntity<StandardError> invalidJwtAuthenticationToken(InvalidJwtAuthenticationToken e, HttpServletRequest http) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new StandardError(
                LocalDateTime.now(),
                HttpStatus.FORBIDDEN.value(),
                e.getMessage(),
                http.getRequestURI()));
    }
}
