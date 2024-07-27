package com.atmosware.belatrix.managmentService.dataAccess;

import com.atmosware.belatrix.managmentService.entities.concretes.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRoleRepository extends JpaRepository<UserRole, UUID> {
    List<UserRole> findAllByUserId(UUID userId);

    boolean existsByUserIdAndRoleId(UUID userId, UUID roleId);

    Optional<UserRole> findByUserIdAndRoleId(UUID userId, UUID roleId);
}