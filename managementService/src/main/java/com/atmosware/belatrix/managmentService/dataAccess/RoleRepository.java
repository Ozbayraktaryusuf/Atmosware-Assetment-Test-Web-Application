package com.atmosware.belatrix.managmentService.dataAccess;

import com.atmosware.belatrix.managmentService.entities.concretes.Roles;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface RoleRepository extends JpaRepository<Roles, UUID> {
    Optional<Roles> findByName(String name);
}
