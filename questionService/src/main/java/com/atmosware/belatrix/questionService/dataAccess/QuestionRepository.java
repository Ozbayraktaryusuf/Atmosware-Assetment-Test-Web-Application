package com.atmosware.belatrix.questionService.dataAccess;

import com.atmosware.belatrix.questionService.entities.concretes.Question;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface QuestionRepository extends JpaRepository<Question, Long> {
    List<Question> findByOrganizationIdOrOrganizationIdIsNull(UUID organizationId);

    Page<Question> findByOrganizationIdOrOrganizationIdIsNull(UUID organizationId, Pageable pageable);

    Optional<Question> findByIdAndOrganizationId(Long id, UUID organizationId);

    //Todo: sonra bak,
    Page<Question> findByTextLike(String text, Pageable pageable);
}
