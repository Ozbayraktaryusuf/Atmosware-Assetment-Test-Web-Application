package com.atmosware.belatrix.managmentService.dataAccess;

import com.atmosware.belatrix.managmentService.entities.concretes.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);
}
