package com.atmosware.belatrix.examSercvice.dataAccess;

import com.atmosware.belatrix.examSercvice.entities.concretes.TestRule;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TestRuleRepository extends JpaRepository<TestRule, Long> {
    Optional<TestRule> findByTestIdAndRuleId(Long testId, Long ruleId);
    Page<TestRule> findByTestId(Long testId, Pageable pageable);
}
