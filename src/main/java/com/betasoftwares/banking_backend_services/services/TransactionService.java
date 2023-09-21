package com.betasoftwares.banking_backend_services.services;

import com.betasoftwares.banking_backend_services.dto.BankResponse;
import com.betasoftwares.banking_backend_services.dto.TransactionDto;
import com.betasoftwares.banking_backend_services.entities.Transaction;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service public interface TransactionService {
    void saveTransaction(TransactionDto transaction);
}
