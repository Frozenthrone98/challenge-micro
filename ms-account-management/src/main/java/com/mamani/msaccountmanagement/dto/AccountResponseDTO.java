package com.mamani.msaccountmanagement.dto;

import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Builder
public record AccountResponseDTO(
        UUID accountId,
        String accountNumber,
        String accountType,
        BigDecimal initialBalance,
        Boolean status,
        LocalDateTime createdAt,
        String customerFullName
) {
}
