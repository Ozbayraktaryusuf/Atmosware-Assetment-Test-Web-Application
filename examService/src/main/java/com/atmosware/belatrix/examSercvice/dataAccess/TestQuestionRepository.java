package com.atmosware.belatrix.examSercvice.dataAccess;

import com.atmosware.belatrix.examSercvice.entities.concretes.TestQuestion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TestQuestionRepository extends JpaRepository<TestQuestion,Long> {
}
