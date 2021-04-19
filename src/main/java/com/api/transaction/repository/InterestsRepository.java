package com.api.transaction.repository;

import com.api.transaction.domain.entity.Interest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InterestsRepository extends JpaRepository<Interest, Long> {

}