package fr.uga.miage.m1.reactor.exception.domain;

import fr.uga.miage.m1.reactor.exception.technic.DashboardEntityIsAlreadyDefinedException;

public class DashboardAlreadyDefinedException extends RuntimeException {
    public DashboardAlreadyDefinedException(DashboardEntityIsAlreadyDefinedException e) {
    }
}
