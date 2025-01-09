package fr.uga.miage.m1.client.crypto.request;

import lombok.Builder;

@Builder
public record ClientCryptoTypeRequest(
    ClientCryptoType clientCryptoType
) {
}
