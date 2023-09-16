package com.betasoftwares.banking_backend_services.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @Builder @AllArgsConstructor @NoArgsConstructor
public class EmailDetails {
    private String recipient;
    private String subject;
    private String messageBody;
    private String attachment;
}
