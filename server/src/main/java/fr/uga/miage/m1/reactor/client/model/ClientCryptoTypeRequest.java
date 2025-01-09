package fr.uga.miage.m1.reactor.client.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ClientCryptoTypeRequest {
    ClientCryptoTypeDTO clientCryptoType;
}
