package com.atmosware.belatrix.examSercvice.dataAccess;

import com.atmosware.belatrix.examSercvice.entities.concretes.TestQuestion;
import org.apache.catalina.LifecycleState;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TestQuestionRepository extends JpaRepository<TestQuestion,Long> {
    Optional<TestQuestion> findByTestIdAndQuestionId(Long testId,Long questionId);
    List<TestQuestion> findAllByQuestionId(Long questionId);
}
