package com.atmosware.belatrix.questionService.dataAccess;

import com.atmosware.belatrix.questionService.entities.concretes.Question;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface QuestionRepository extends JpaRepository<Question,Long> {

    List<Question> findByOrganizationIdOrOrganizationIdIsNull(UUID organizationId);
}
