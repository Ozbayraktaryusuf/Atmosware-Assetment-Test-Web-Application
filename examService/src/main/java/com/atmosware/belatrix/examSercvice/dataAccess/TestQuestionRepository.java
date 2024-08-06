package com.atmosware.belatrix.examSercvice.dataAccess;

import com.atmosware.belatrix.examSercvice.entities.concretes.TestQuestion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TestQuestionRepository extends JpaRepository<TestQuestion,Long> {
    Optional<TestQuestion> findByTestIdAndQuestionId(Long testId,Long questionId);
}
