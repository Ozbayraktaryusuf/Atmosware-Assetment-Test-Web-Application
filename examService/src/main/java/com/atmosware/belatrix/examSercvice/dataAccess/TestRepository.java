package com.atmosware.belatrix.examSercvice.dataAccess;

import com.atmosware.belatrix.examSercvice.entities.concretes.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TestRepository extends JpaRepository<Test,Long> {
    Page<Test> findByOrganizationIdOrOrganizationIdIsNull(UUID organizationId, Pageable pageable);
}
