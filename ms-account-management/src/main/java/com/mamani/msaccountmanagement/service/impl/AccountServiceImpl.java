package com.mamani.msaccountmanagement.service.impl;

import com.mamani.msaccountmanagement.domain.entity.Account;
import com.mamani.msaccountmanagement.domain.entity.AccountType;
import com.mamani.msaccountmanagement.domain.repository.IAccountRepository;
import com.mamani.msaccountmanagement.domain.repository.IAccountTypeRepository;
import com.mamani.msaccountmanagement.dto.AccountRequestDTO;
import com.mamani.msaccountmanagement.dto.AccountResponseDTO;
import com.mamani.msaccountmanagement.dto.CustomerResponseDTO;
import com.mamani.msaccountmanagement.exception.ModelNotFoundException;
import com.mamani.msaccountmanagement.service.IAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements IAccountService {

    @Value("${api.customer.path}")
    private String externalBasePath;
    private final IAccountRepository accountRepository;
    private final IAccountTypeRepository accountTypeRepository;
    private final WebClient webClient;

    @Override
    @Transactional
    public AccountResponseDTO save(AccountRequestDTO accountRequestDto) throws Exception {

        Account account = convertToEntity(accountRequestDto);
        account.setCreatedAt(LocalDateTime.now());
        account.setStatus(true);
        return convertToResponseDto(accountRepository.save(account));
    }

    @Override
    @Transactional
    public AccountResponseDTO update(AccountRequestDTO accountRequestDto, UUID id) throws Exception {

        Account dbAccount = accountRepository.findById(id).orElseThrow(() -> new ModelNotFoundException("ID ACCOUNT NOT FOUND: " + id));
        Account updateAccount = convertToEntity(accountRequestDto);

        updateAccount.setAccountId(dbAccount.getAccountId());
        updateAccount.setCreatedAt(dbAccount.getCreatedAt());
        updateAccount.setStatus(dbAccount.getStatus());

        return convertToResponseDto(accountRepository.save(updateAccount));
    }

    @Override
    public List<AccountResponseDTO> readAll() throws Exception {
        return accountRepository.findAll().stream()
                .filter(account -> account.getStatus())
                .map(this::convertToResponseDto).toList();
    }

    @Override
    public AccountResponseDTO readById(UUID id) throws Exception {
        Account account = accountRepository.findById(id).orElseThrow();
        return convertToResponseDto(account) ;
    }

    @Override
    public void delete(UUID id) throws Exception {
        Account account = accountRepository.findById(id).orElseThrow(() -> new ModelNotFoundException("ID ACCOUNT NOT FOUND: " + id));
        accountRepository.deleteById(id);
    }

    @Override
    public void disableAccount(UUID id) throws Exception {
        Account account = accountRepository.findById(id).filter(acc -> acc.getStatus()).orElseThrow();
        account.setStatus(false);
        accountRepository.save(account);
    }

    public Account convertToEntity(AccountRequestDTO accountRequestDto){
        AccountType accountType = accountTypeRepository.findById(accountRequestDto.accountTypeId()).orElseThrow(() -> new ModelNotFoundException("ID ACCOUNT NOT FOUND: " + accountRequestDto.accountTypeId()));

        return Account.builder()
                .accountNumber(accountRequestDto.accountNumber())
                .accountType(accountType)
                .initialBalance(accountRequestDto.initialBalance())
                .customerId(accountRequestDto.customerId())
                .build();
    }

    public AccountResponseDTO convertToResponseDto(Account account){

        Mono<CustomerResponseDTO> customerResponseDTO = webClient.get()
                .uri(externalBasePath + account.getCustomerId())
                .retrieve()
                .bodyToMono(CustomerResponseDTO.class)
                .switchIfEmpty(Mono.error(() -> new ModelNotFoundException("ID CUSTOMER NOT FOUND: " + account.getCustomerId())));

        CustomerResponseDTO response = customerResponseDTO.block();

        return AccountResponseDTO.builder()
                .accountId(account.getAccountId())
                .accountNumber(account.getAccountNumber())
                .accountType(account.getAccountType().getDescription())
                .initialBalance(account.getInitialBalance())
                .status(account.getStatus())
                .createdAt(account.getCreatedAt())
                .customerFullName(response.fullName())
                .build();
    }

}
