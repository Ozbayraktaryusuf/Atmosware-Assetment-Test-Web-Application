package com.atmosware.belatrix.examSercvice.dataAccess;

import com.atmosware.belatrix.examSercvice.entities.concretes.Rule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RuleRepository extends JpaRepository<Rule,Long> {
}
