package com.betasoftwares.banking_backend_services.services;

import com.betasoftwares.banking_backend_services.dto.BankResponse;
import com.betasoftwares.banking_backend_services.dto.TransactionDto;
import com.betasoftwares.banking_backend_services.entities.Transaction;
import com.betasoftwares.banking_backend_services.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.Optional;

public class TransactionServiceImpl implements TransactionService{

    @Autowired
    TransactionRepository transactionRepository;
    @Override
    public void saveTransaction(TransactionDto transaction) {
        Transaction transactionDetail = Transaction
                .builder()
                .amount(transaction.getAmount())
                .transactionType(transaction.getTransactionType())
                .transactionStatus("Success")
                .accountNumber(transaction.getAccountNumber())
                .createDate(LocalDate.now())
                .build();
        transactionRepository.save(transactionDetail);
    }
}
