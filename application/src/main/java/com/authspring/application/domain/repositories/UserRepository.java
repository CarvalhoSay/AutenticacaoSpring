package com.authspring.application.domain.repositories;

import com.authspring.application.domain.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
}
