package com.betasoftwares.banking_backend_services.services;

import com.betasoftwares.banking_backend_services.dto.BankResponse;
import com.betasoftwares.banking_backend_services.dto.UserRequest;

public interface UserService {
    BankResponse createAccount(UserRequest userRequest);
}
