package com.betasoftwares.banking_backend_services.services;

import com.betasoftwares.banking_backend_services.dto.AccountInfo;
import com.betasoftwares.banking_backend_services.dto.BankResponse;
import com.betasoftwares.banking_backend_services.dto.EmailDetails;
import com.betasoftwares.banking_backend_services.dto.UserRequest;
import com.betasoftwares.banking_backend_services.entities.User;
import com.betasoftwares.banking_backend_services.repository.UserRepository;
import com.betasoftwares.banking_backend_services.utils.AccountUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
@Service public class UserServiceImp implements UserService{
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailService emailService;
    @Override
    public BankResponse createAccount(UserRequest userRequest) {

        /**
         * Creating an account - saving a new user to db
         * Check if use already has an account
         */
        if(userRepository.existsByEmail(userRequest.getEmail())){
            return BankResponse.builder()
                    .responseCode(AccountUtils.ACCOUNT_EXIST_CODE)
                    .responseMessage(AccountUtils.ACCOUNT_EXIST_MESSAGE)
                    .accountInfo(null)
                    .build();

        }
        User newUser = User.builder()
                .firstName(userRequest.getFirstName())
                .lastName(userRequest.getLastName())
                .otherName(userRequest.getOtherName())
                .address(userRequest.getAddress())
                .stateOfOrigin(userRequest.getStateOfOrigin())
                .gender(userRequest.getGender())
                .accountNumber(AccountUtils.generateAccountNumber())
                .accountBalance(BigDecimal.ZERO)
                .email(userRequest.getEmail())
                .phoneNumber(userRequest.getPhoneNumber())
                .alternativePhoneNumber(userRequest.getAlternativePhoneNumber())
                .status("ACTIVE")
                .build();

        User savedUser = userRepository.save(newUser);
        // Send Email Alert
        EmailDetails emailDetails = EmailDetails
                .builder()
                .recipient(savedUser.getEmail())
                .subject("ACCOUNT CREATION")
                .messageBody("Congratulations your account has been successfully created\n" +
                                "Account Details:\n Account Name: " + savedUser.getFirstName() + " " + savedUser.getLastName() +
                                " " +  savedUser.getLastName()+ "\nAccount Number: " + savedUser.getAccountNumber())
                .build();
        emailService.sendEmailAlert(emailDetails);
        return BankResponse.builder()
                .responseCode(AccountUtils.ACCOUNT_CREATION_SUCCESS)
                .responseMessage(AccountUtils.ACCOUNT_CREATION_MESSAGE)
                .accountInfo(AccountInfo.builder()
                        .accountBalance(savedUser.getAccountBalance())
                        .accountName(savedUser.getAccountNumber())
                        .accountNumber(savedUser.getFirstName() + " " + savedUser.getLastName() + " " + savedUser.getLastName())
                        .build())
                .build();
    }
}
