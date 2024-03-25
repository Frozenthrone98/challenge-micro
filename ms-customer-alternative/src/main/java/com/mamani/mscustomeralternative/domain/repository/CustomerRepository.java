package com.mamani.mscustomeralternative.domain.repository;

import com.mamani.mscustomeralternative.domain.entity.Customer;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CustomerRepository extends GenericRepository<Customer,UUID> {
}
