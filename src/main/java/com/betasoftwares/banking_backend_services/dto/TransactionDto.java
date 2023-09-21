package com.betasoftwares.banking_backend_services.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Builder @Data @NoArgsConstructor @AllArgsConstructor
public class TransactionDto {
    private String accountNumber;
    private BigDecimal amount;
    private String transactionType;
    private String transactionStatus;
}
