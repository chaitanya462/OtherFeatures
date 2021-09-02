package com.simplify.marketplace.repository;

import com.simplify.marketplace.domain.Employment;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Employment entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EmploymentRepository extends JpaRepository<Employment, Long> {}
