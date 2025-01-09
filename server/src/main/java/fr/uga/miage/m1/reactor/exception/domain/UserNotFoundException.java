package fr.uga.miage.m1.reactor.exception.domain;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(String message) {
        super(message);
    }
}
