package com.mamani.msaccountmanagement.domain.repository;


import com.mamani.msaccountmanagement.domain.entity.Movement;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IMovementRepository extends IGenericRepository<Movement, Long> {

    @Query("SELECT m FROM Movement m " +
            "WHERE m.account.accountId = :accountId " +
            "ORDER BY m.createdAt DESC LIMIT 1")
    Optional<Movement> findLastMovementByAccount(@Param("accountId") UUID accountId) throws Exception;

    @Query("SELECT m FROM Movement m " +
            "WHERE m.createdAt BETWEEN :start AND :end ")
    List<Movement> findAllBetweenDates(@Param("start")LocalDateTime start,@Param("end") LocalDateTime end) throws Exception;
}
