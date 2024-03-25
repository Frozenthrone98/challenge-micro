package com.mamani.msaccountmanagement.domain.repository;

import com.mamani.msaccountmanagement.domain.entity.Account;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface IAccountRepository extends IGenericRepository<Account, UUID> {
    @Query("SELECT a FROM Account a " +
            "WHERE a.customerId = :customerId " +
            "AND a.status = true ")
    Optional<List<Account>> findAccountByCustomerId(@Param("customerId") UUID customerId) throws Exception;
}
