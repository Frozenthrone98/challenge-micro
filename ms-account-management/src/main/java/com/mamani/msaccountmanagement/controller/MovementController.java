package com.mamani.msaccountmanagement.controller;

import com.mamani.msaccountmanagement.dto.MovementRequestDTO;
import com.mamani.msaccountmanagement.dto.MovementResponseDTO;
import com.mamani.msaccountmanagement.service.IMovementService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/movements")
public class MovementController {
    private final IMovementService movementService;

    @PostMapping
    public ResponseEntity<MovementResponseDTO> createMovement(@RequestBody MovementRequestDTO movementRequestDto) throws Exception {

        MovementResponseDTO response = movementService.save(movementRequestDto);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{movementId}")
                .buildAndExpand(response.movementId())
                .toUri();
        log.info("[MovementController] - Create Movement : " + response.movementId());
        return ResponseEntity.created(location).body(response);
    }

    @GetMapping("/reports")
    public ResponseEntity<List<MovementResponseDTO>> getAllMovementsByDateAndCustomerId(@RequestParam(required = true) LocalDate start,
                                                                                        @RequestParam(required = true) LocalDate end,
                                                                                        @RequestParam(required = true) UUID customerId
                                                                                        ) throws Exception {

        List<MovementResponseDTO> response = movementService.getAllMovementsByCustomerId(customerId,start,end);

        return ResponseEntity.ok(response);
    }
}
