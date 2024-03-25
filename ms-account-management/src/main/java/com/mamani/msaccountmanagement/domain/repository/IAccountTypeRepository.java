package com.mamani.msaccountmanagement.domain.repository;

import com.mamani.msaccountmanagement.domain.entity.AccountType;
import org.springframework.stereotype.Repository;

@Repository
public interface IAccountTypeRepository extends IGenericRepository<AccountType,Integer> {
}
