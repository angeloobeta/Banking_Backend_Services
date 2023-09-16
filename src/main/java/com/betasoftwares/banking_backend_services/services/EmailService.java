package com.betasoftwares.banking_backend_services.services;

import com.betasoftwares.banking_backend_services.dto.EmailDetails;

public interface EmailService {
    void sendEmailAlert(EmailDetails emailDetails);
}
