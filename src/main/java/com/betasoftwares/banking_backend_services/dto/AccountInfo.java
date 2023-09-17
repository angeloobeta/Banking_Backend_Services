package com.betasoftwares.banking_backend_services.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

@Data @Builder @AllArgsConstructor @NoArgsConstructor
public class AccountInfo {
    private String accountName;
    private BigDecimal accountBalance;
    private  String accountNumber;
    private LocalDateTime accountCreationDate;
}
