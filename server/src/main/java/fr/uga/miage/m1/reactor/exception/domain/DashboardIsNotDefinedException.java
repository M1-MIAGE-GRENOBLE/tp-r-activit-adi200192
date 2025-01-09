package fr.uga.miage.m1.reactor.exception.domain;

import fr.uga.miage.m1.reactor.exception.technic.DashboardEntityIsNotDefinedDefinedException;

public class DashboardIsNotDefinedException extends RuntimeException {
    public DashboardIsNotDefinedException(DashboardEntityIsNotDefinedDefinedException e) {
        super(e);
    }
}
