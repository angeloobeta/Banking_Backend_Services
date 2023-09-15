package com.betasoftwares.banking_backend_services.repository;

import com.betasoftwares.banking_backend_services.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
