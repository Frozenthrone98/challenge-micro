package com.mamani.mscustomeralternative.dto;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
public record CustomerDTO(
        UUID customerId,
        String password,
        Boolean status,
        String fullName,
        String gender,
        int age,
        String identificationNumber,
        String address,
        String phoneNumber,
        LocalDateTime createdAt
) {
}
