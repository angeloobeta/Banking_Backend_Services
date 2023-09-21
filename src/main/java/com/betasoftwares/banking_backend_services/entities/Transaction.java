package com.betasoftwares.banking_backend_services.entities;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.math.BigDecimal;


@Getter @Setter @Builder
@NoArgsConstructor @AllArgsConstructor
@Table(name = "transaction")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String transactionId;
    private String accountNumber;
    private BigDecimal amount;
    private String transactionType;
    private String transactionStatus;
}
