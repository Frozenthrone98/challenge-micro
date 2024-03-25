package com.mamani.msaccountmanagement.dto;

import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
public record MovementResponseDTO(
        Long movementId,
        LocalDateTime createdAt,
        String customerFullName,
        String accountNumber,
        String accountType,
        BigDecimal initialBalance,
        Boolean stateAccount,
        BigDecimal amountMovement,
        BigDecimal availableBalance

) {
}
