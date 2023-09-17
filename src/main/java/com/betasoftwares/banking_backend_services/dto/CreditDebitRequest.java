package com.betasoftwares.banking_backend_services.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
@AllArgsConstructor @Data
@NoArgsConstructor  public class CreditDebitRequest {
    private String accountNumber;
    private BigDecimal amount;
}
