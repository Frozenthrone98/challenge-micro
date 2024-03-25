package com.mamani.msaccountmanagement.domain.repository;

import com.mamani.msaccountmanagement.domain.entity.MovementType;
import org.springframework.stereotype.Repository;

@Repository
public interface IMovementTypeRepository extends IGenericRepository<MovementType, Integer> {
}
