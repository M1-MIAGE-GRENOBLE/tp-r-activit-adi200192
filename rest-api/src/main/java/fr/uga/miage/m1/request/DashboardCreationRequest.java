package fr.uga.miage.m1.request;

import lombok.Builder;

import java.util.Collection;

@Builder
public record DashboardCreationRequest(
        Collection<CryptoTypeRequest> cryptos
) {
}
