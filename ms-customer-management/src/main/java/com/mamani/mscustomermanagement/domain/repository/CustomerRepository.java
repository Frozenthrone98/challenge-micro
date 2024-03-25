package com.mamani.mscustomermanagement.domain.repository;

import com.mamani.mscustomermanagement.domain.entity.Customer;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CustomerRepository extends GenericRepository<Customer,UUID> {
}
