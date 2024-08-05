package com.atmosware.belatrix.examSercvice.dataAccess;

import com.atmosware.belatrix.examSercvice.entities.concretes.TestRule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TestRuleRepository extends JpaRepository<TestRule,Long> {
}
