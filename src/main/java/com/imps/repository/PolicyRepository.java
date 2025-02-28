package com.imps.repository;

import com.imps.entity.InsurancePolicy;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface PolicyRepository extends JpaRepository<InsurancePolicy, Long> {
    List<InsurancePolicy> findByUserId(Long userId);
    Optional<InsurancePolicy> findByIdAndUserId(Long id, Long userId);
}