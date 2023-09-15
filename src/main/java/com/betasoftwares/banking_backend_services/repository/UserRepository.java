package com.betasoftwares.banking_backend_services.repository;

import com.betasoftwares.banking_backend_services.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Boolean existByEmail(String email);
}
