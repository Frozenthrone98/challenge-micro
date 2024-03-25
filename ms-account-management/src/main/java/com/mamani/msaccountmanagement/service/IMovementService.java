package com.mamani.msaccountmanagement.service;

import com.mamani.msaccountmanagement.dto.MovementRequestDTO;
import com.mamani.msaccountmanagement.dto.MovementResponseDTO;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface IMovementService {
    MovementResponseDTO save(MovementRequestDTO movementRequestDTO) throws Exception;
    List<MovementResponseDTO> getAllMovementsByCustomerId(UUID customerId, LocalDate start, LocalDate end) throws Exception;


}
