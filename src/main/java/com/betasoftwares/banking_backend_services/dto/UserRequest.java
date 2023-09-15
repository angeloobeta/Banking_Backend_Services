package com.betasoftwares.banking_backend_services.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data @Builder @AllArgsConstructor @NoArgsConstructor
public class UserRequest {
    private String firstName;
    private String lastName;
    private String otherName;
    private String address;
    private String stateOfOrigin;
    private String gender;
    private String email;
    private String phoneNumber;
    private String alternativePhoneNumber;
    private String status;
}
