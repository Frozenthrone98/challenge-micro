package com.mamani.msaccountmanagement.dto;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
public record CustomerResponseDTO(
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
