package com.api.transaction.repository;

import com.api.transaction.domain.entity.Operation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface OperationsRepository extends JpaRepository<Operation, Long> {

}
