package com.mamani.msaccountmanagement.controller;

import com.mamani.msaccountmanagement.dto.AccountRequestDTO;
import com.mamani.msaccountmanagement.dto.AccountResponseDTO;
import com.mamani.msaccountmanagement.service.IAccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/accounts")
public class AccountController {

    private final IAccountService accountService;

    @GetMapping
    public ResponseEntity<List<AccountResponseDTO>> getAccounts() throws Exception {

        List<AccountResponseDTO> response = accountService.readAll();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{accountId}")
    public ResponseEntity<AccountResponseDTO> findAccountById(@PathVariable UUID accountId) throws Exception {

        AccountResponseDTO response = accountService.readById(accountId);

        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<AccountResponseDTO> createAccount(@Valid @RequestBody AccountRequestDTO accountRequestDto) throws Exception {

        AccountResponseDTO response = accountService.save(accountRequestDto);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{accountId}")
                .buildAndExpand(response.accountId())
                .toUri();
        log.info("[AccountController] - Create Account : " + response.accountId());
        return ResponseEntity.created(location).body(response);
    }

    @PutMapping("/{accountId}")
    public ResponseEntity<AccountResponseDTO> updateAccount(@PathVariable UUID accountId,
                                                            @Valid @RequestBody AccountRequestDTO accountRequestDto) throws Exception {
        AccountResponseDTO response = accountService.update(accountRequestDto, accountId);
        log.info("[AccountController] - Update Account : " + response.accountId());
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{accountId}")
    public ResponseEntity<Void> disableAccount(@PathVariable UUID accountId) throws Exception {

        accountService.disableAccount(accountId);
        log.info("[AccountController] - Disable Account : " + accountId);
        return ResponseEntity.noContent().build();
    }

}
