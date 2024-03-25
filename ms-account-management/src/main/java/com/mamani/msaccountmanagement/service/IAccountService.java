package com.mamani.msaccountmanagement.service;


import com.mamani.msaccountmanagement.domain.entity.Account;
import com.mamani.msaccountmanagement.dto.AccountRequestDTO;
import com.mamani.msaccountmanagement.dto.AccountResponseDTO;

import java.math.BigDecimal;
import java.util.UUID;

public interface IAccountService extends ICRUD<AccountRequestDTO, AccountResponseDTO, UUID>{
    void disableAccount(UUID id) throws Exception;
//    boolean validateAccountBalanceToTransfer(String accountNumber, BigDecimal value) throws Exception;
}
