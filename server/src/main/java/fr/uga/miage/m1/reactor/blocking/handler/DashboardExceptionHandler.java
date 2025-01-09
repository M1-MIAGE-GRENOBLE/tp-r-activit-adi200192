package fr.uga.miage.m1.reactor.blocking.handler;

import fr.uga.miage.m1.reactor.exception.domain.DashboardAlreadyDefinedException;
import fr.uga.miage.m1.reactor.exception.domain.DashboardIsNotDefinedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class DashboardExceptionHandler {

    @ExceptionHandler(DashboardAlreadyDefinedException.class)
    public ResponseEntity<String> handle(DashboardAlreadyDefinedException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(DashboardIsNotDefinedException.class)
    public ResponseEntity<String> handle(DashboardIsNotDefinedException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }
}
