package com.betasoftwares.banking_backend_services.repository;

import com.betasoftwares.banking_backend_services.entities.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, String> {
}
