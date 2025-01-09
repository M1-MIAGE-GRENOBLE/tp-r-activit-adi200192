package fr.uga.miage.m1.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CryptoResponse {
    CryptoTypeResponse type;
    String name;
    Integer safetyIndex;
    boolean externeSource;
}
