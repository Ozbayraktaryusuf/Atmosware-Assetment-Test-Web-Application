package com.atmosware.belatrix.managmentService.dataAccess;

import com.atmosware.belatrix.managmentService.entities.concretes.Organization;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface OrganizationRepository extends JpaRepository<Organization,UUID > {
    Optional<Organization> findByOrganizationName(String organizationName);
}
