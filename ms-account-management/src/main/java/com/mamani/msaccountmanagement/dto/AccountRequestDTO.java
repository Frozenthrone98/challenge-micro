package com.mamani.msaccountmanagement.dto;


import jakarta.validation.constraints.Min;
import lombok.Builder;

import java.math.BigDecimal;
import java.util.UUID;

@Builder
public record AccountRequestDTO(
         String accountNumber,
         Integer accountTypeId,
         @Min(value = 1)
         BigDecimal initialBalance,
         UUID customerId
) {
}
