package com.mamani.msaccountmanagement.dto;

import lombok.Builder;

import java.math.BigDecimal;
import java.util.UUID;

@Builder
public record MovementRequestDTO(
        Integer movementTypeId,
        BigDecimal value,
        UUID accountId
) {
}
