package com.mamani.msaccountmanagement.service.impl;

import com.mamani.msaccountmanagement.domain.entity.Account;
import com.mamani.msaccountmanagement.domain.entity.Movement;
import com.mamani.msaccountmanagement.domain.entity.MovementType;
import com.mamani.msaccountmanagement.domain.repository.IAccountRepository;
import com.mamani.msaccountmanagement.domain.repository.IMovementRepository;
import com.mamani.msaccountmanagement.domain.repository.IMovementTypeRepository;
import com.mamani.msaccountmanagement.dto.CustomerResponseDTO;
import com.mamani.msaccountmanagement.dto.MovementRequestDTO;
import com.mamani.msaccountmanagement.dto.MovementResponseDTO;
import com.mamani.msaccountmanagement.exception.ModelNotFoundException;
import com.mamani.msaccountmanagement.service.IMovementService;
import com.mamani.msaccountmanagement.util.Constants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class MovementServiceImpl implements IMovementService {
    @Value("${api.customer.path}")
    private String externalBasePath;
    private final IMovementRepository movementRepository;
    private final IAccountRepository accountRepository;
    private final IMovementTypeRepository movementTypeRepository;
    private final WebClient webClient;

    @Override
    public MovementResponseDTO save(MovementRequestDTO movementRequestDto) throws Exception{
        // Mount equal Zero
        if(movementRequestDto.value().signum() == Constants.ZERO){
            throw new Exception("VALUE NOT ALLOW: " + Constants.ZERO);
        }else {
            Movement movement = convertToEntity(movementRequestDto);
            movement = validateMovementTypeAndAmountSign(movementRequestDto, movement);
            return convertToResponseDto(movementRepository.save(movement));
        }
    }

    @Override
    public List<MovementResponseDTO> getAllMovementsByCustomerId(UUID customerId, LocalDate start, LocalDate end) throws Exception{
        LocalTime timeZero = LocalTime.of(Constants.ZERO, Constants.ZERO, Constants.ZERO);

        List<Account> accounts = accountRepository.findAccountByCustomerId(customerId).orElseThrow(() -> new ModelNotFoundException("ID CUSTOMER NOT FOUND: " + customerId));

        return movementRepository.findAllBetweenDates(start.atTime(timeZero),end.atTime(timeZero)).stream()
                .filter(movement -> accounts.stream()
                        .anyMatch(account -> account.getAccountId().equals(movement.getAccount().getAccountId())))
                .map(movement -> convertToResponseDto(movement))
                .toList();
    }

    public Movement convertToEntity(MovementRequestDTO movementRequestDto){
        MovementType movementType = movementTypeRepository.findById(movementRequestDto.movementTypeId())
                .orElseThrow(() -> new ModelNotFoundException("ID MOVEMENT TYPE NOT FOUND: " + movementRequestDto.movementTypeId()));
        Account account = accountRepository.findById(movementRequestDto.accountId())
                .orElseThrow(() -> new ModelNotFoundException("ID MOVEMENT NOT FOUND: " + movementRequestDto.accountId()));

        return Movement.builder()
                .movementType(movementType)
                .value(movementRequestDto.value())
                .account(account)
                .build();
    }

    public MovementResponseDTO convertToResponseDto(Movement movement){

        CustomerResponseDTO customerResponseDTO = getCustomerResponseDto(movement);

        return MovementResponseDTO.builder()
                .movementId(movement.getMovementId())
                .createdAt(movement.getCreatedAt())
                .customerFullName(customerResponseDTO.fullName())
                .accountNumber(movement.getAccount().getAccountNumber())
                .accountType(movement.getAccount().getAccountType().getDescription())
                .initialBalance(movement.getAccount().getInitialBalance())
                .stateAccount(movement.getAccount().getStatus())
                .amountMovement(movement.getValue())
                .availableBalance(movement.getBalance())
                .build();
    }

    public Movement validateMovementTypeAndAmountSign(MovementRequestDTO movementRequestDto, Movement movement) throws Exception {

        boolean sufficientBalance = false;

        // Movement Type Withdrawal and Value Negative
        if(movementRequestDto.movementTypeId() == Constants.MOVEMENT_TYPE_ID_WITHDRAWAL && movementRequestDto.value().signum() < Constants.ZERO) {
            Optional<Movement> lastMovement = movementRepository.findLastMovementByAccount(movement.getAccount().getAccountId());
            if(lastMovement.isPresent()){

                sufficientBalance = validateAccountSufficientBalance(lastMovement.get().getAccount(), movementRequestDto.value());

                if(sufficientBalance){
                    movement.setCreatedAt(LocalDateTime.now());
                    movement.setBalance(lastMovement.get().getBalance().add(movementRequestDto.value()));
                }else {
                    throw new Exception("BALANCE INSUFFICIENT");
                }

            }else {

                sufficientBalance = validateAccountSufficientBalance(movement.getAccount(),movementRequestDto.value());

                if(sufficientBalance){
                    movement.setCreatedAt(LocalDateTime.now());
                    movement.setBalance(movement.getAccount().getInitialBalance().add(movementRequestDto.value()));
                }else {
                    throw new Exception("BALANCE INSUFFICIENT");
                }
            }
        }
        // Movement Type Deposit and Value Positive
        if(movementRequestDto.movementTypeId() == Constants.MOVEMENT_TYPE_ID_DEPOSIT && movementRequestDto.value().signum() > Constants.ZERO) {
            movement.setCreatedAt(LocalDateTime.now());
            Optional<Movement> lastMovement = movementRepository.findLastMovementByAccount(movement.getAccount().getAccountId());

            if(lastMovement.isPresent()){
                movement.setBalance(lastMovement.get().getBalance().add(movementRequestDto.value()));
            }else movement.setBalance(movement.getAccount().getInitialBalance().add(movementRequestDto.value()));
        }
        return movement;
    }

    public boolean validateAccountSufficientBalance(Account account, BigDecimal value) throws Exception{
        int result = -1;

        result = account.getInitialBalance().compareTo(value);

        if( result < Constants.ZERO){
            throw new Exception();
        } else if(result == Constants.ZERO){
            return true;
        } else {
            return true;
        }
    }

    public CustomerResponseDTO getCustomerResponseDto(Movement movement){
        Mono<CustomerResponseDTO> customerResponseDTO = webClient.get()
                .uri(externalBasePath + movement.getAccount().getCustomerId())
                .retrieve()
                .bodyToMono(CustomerResponseDTO.class)
                .switchIfEmpty(Mono.error(new ModelNotFoundException("ID CUSTOMER NOT FOUND: " + movement.getAccount().getCustomerId())));
        return customerResponseDTO.block();
    }
}
