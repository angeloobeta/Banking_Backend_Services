package com.betasoftwares.banking_backend_services.services;

import com.betasoftwares.banking_backend_services.dto.*;
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
         * Check if you use already has an account
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
                                "Account Details:\n \tAccount Name: " + savedUser.getFirstName() + " " + savedUser.getLastName() +
                                " " +  savedUser.getOtherName()+ "\n \tAccount Number: " + savedUser.getAccountNumber())
                .build();
        emailService.sendEmailAlert(emailDetails);
        return BankResponse.builder()
                .responseCode(AccountUtils.ACCOUNT_CREATION_SUCCESS_CODE)
                .responseMessage(AccountUtils.ACCOUNT_CREATION_SUCCESS_MESSAGE)
                .accountInfo(AccountInfo.builder()
                        .accountCreationDate(savedUser.getCreatedAt())
                        .accountBalance(savedUser.getAccountBalance())
                        .accountNumber(savedUser.getAccountNumber())
                        .accountName(savedUser.getFirstName() + " " + savedUser.getLastName() + " " + savedUser.getOtherName())
                        .build())
                .build();
    }

    @Override
    public BankResponse balanceEnquiry(EnquiryRequest enquiryRequest) {
        if(!userRepository.existsByAccountNumber(enquiryRequest.getAccountNumber())){
            return BankResponse
                    .builder()
                    .responseCode(AccountUtils.ACCOUNT_DOES_NOT_EXIST_CODE)
                    .responseMessage(AccountUtils.ACCOUNT_DOES_NOT_EXIST_MESSAGE)
                    .accountInfo(null)
                    .build();

        }
        User foundUser = userRepository.findByAccountNumber(enquiryRequest.getAccountNumber());
        return  BankResponse
                .builder()
                .accountInfo(AccountInfo
                        .builder()
                        .accountNumber(enquiryRequest.getAccountNumber())
                        .accountName(foundUser.getFirstName() + " " + foundUser.getLastName() + " " + foundUser.getOtherName())
                        .accountBalance(foundUser.getAccountBalance())
                        .accountCreationDate(foundUser.getCreatedAt())
                        .build())
                .responseCode(AccountUtils.ACCOUNT_FOUND_CODE)
                .responseMessage(AccountUtils.ACCOUNT_FOUND_SUCCESS)
                .build();
    }

    @Override
    public String nameEnquiry(EnquiryRequest enquiryRequest) {
        if(!userRepository.existsByAccountNumber(enquiryRequest.getAccountNumber())){
            return AccountUtils.ACCOUNT_DOES_NOT_EXIST_MESSAGE;}

        User foundUser = userRepository.findByAccountNumber(enquiryRequest.getAccountNumber());
        return foundUser.getFirstName() + " " + foundUser.getLastName() + " " + foundUser.getOtherName();
    }

    @Override
    public BankResponse creditAccount(CreditDebitRequest creditDebitRequest) {
        // check if the account to be credited exist
        if(!userRepository.existsByAccountNumber(creditDebitRequest.getAccountNumber())){
            return BankResponse
                    .builder()
                    .responseMessage(AccountUtils.ACCOUNT_DOES_NOT_EXIST_MESSAGE)
                    .responseCode(AccountUtils.ACCOUNT_DOES_NOT_EXIST_CODE)
                    .accountInfo(null)
                    .build();
        }
        User foundUser = userRepository.findByAccountNumber(creditDebitRequest.getAccountNumber());
        foundUser.setAccountBalance(foundUser.getAccountBalance().add(creditDebitRequest.getAmount()));
        userRepository.save(foundUser);

        return BankResponse
                .builder()
                .responseCode(AccountUtils.ACCOUNT_CREDITED_CODE)
                .responseMessage(AccountUtils.ACCOUNT_CREATION_SUCCESS_MESSAGE)
                .accountInfo(AccountInfo
                        .builder()
                        .accountName(foundUser.getFirstName() + " " + foundUser.getLastName() + " " + foundUser.getOtherName())
                        .accountNumber(creditDebitRequest.getAccountNumber())
                        .accountBalance(foundUser.getAccountBalance())
                        .accountCreationDate(foundUser.getCreatedAt())
                        .build())
                .build();
    }

    @Override
    public BankResponse debitAccount(CreditDebitRequest creditDebitRequest) {
        if(!userRepository.existsByAccountNumber(creditDebitRequest.getAccountNumber())){
            return BankResponse
                    .builder()
                    .responseCode(AccountUtils.ACCOUNT_DOES_NOT_EXIST_CODE)
                    .responseMessage(AccountUtils.ACCOUNT_DOES_NOT_EXIST_MESSAGE)
                    .accountInfo(null)
                    .build();
        }
        User foundUser = userRepository.findByAccountNumber(creditDebitRequest.getAccountNumber());

        // check if the account balance is less than the debit request
        if(foundUser.getAccountBalance().intValue()  <
                creditDebitRequest.getAmount().intValue()){
            return BankResponse
                    .builder()
                    .accountInfo(null)
                    .responseMessage(AccountUtils.INSUFFICIENT_BALANCE_MESSAGE)
                    .responseCode(AccountUtils.INSUFFICIENT_BALANCE_CODE)
                    .build();

        }else{
            foundUser.setAccountBalance(foundUser.getAccountBalance().subtract(creditDebitRequest.getAmount()));
            userRepository.save(foundUser);

            return  BankResponse
                    .builder()
                    .responseCode(AccountUtils.ACCOUNT_DEBITED_CODE)
                    .responseMessage(AccountUtils.ACCOUNT_DEBITED_SUCCESS_MESSAGE)
                    .accountInfo(AccountInfo
                            .builder()
                            .accountBalance(foundUser.getAccountBalance())
                            .accountNumber(creditDebitRequest.getAccountNumber())
                            .accountCreationDate(foundUser.getCreatedAt())
                            .accountName(foundUser.getFirstName() + " " + foundUser.getLastName() + " " + foundUser.getOtherName())
                            .build())
                    .build();
        }
    }

    @Override
    public BankResponse transfer(TransferRequest transferRequest) {
        // check if the account of the
        if(!userRepository.existsByAccountNumber(transferRequest.getDestinationAccountNumber()) || !userRepository.existsByAccountNumber(transferRequest.getSourceAccountNumber())){
            return BankResponse
                    .builder()
                    .responseCode(AccountUtils.ACCOUNT_DOES_NOT_EXIST_CODE)
                    .responseMessage(AccountUtils.ACCOUNT_DOES_NOT_EXIST_MESSAGE)
                    .accountInfo(null)
                    .build();

        }
        // fetch account details for the creditor
        User sourceAccount = userRepository.findByAccountNumber(transferRequest.getSourceAccountNumber());
        if(transferRequest.getAmount() > sourceAccount.getAccountBalance().intValue()){
            return BankResponse
                    .builder()
                    .responseCode(AccountUtils.INSUFFICIENT_BALANCE_CODE)
                    .responseMessage(AccountUtils.INSUFFICIENT_BALANCE_MESSAGE)
                    .accountInfo(AccountInfo
                            .builder()
                            .accountCreationDate(sourceAccount.getCreatedAt())
                            .accountNumber(transferRequest.getSourceAccountNumber())
                            .accountBalance(sourceAccount.getAccountBalance())
                            .accountName(sourceAccount.getFirstName() + " " + sourceAccount.getLastName() + " " + sourceAccount.getOtherName())
                            .build())
                    .build();
        }
        // debit the source account
        sourceAccount.setAccountBalance(sourceAccount.getAccountBalance().subtract(BigDecimal.valueOf(transferRequest.getAmount())));
        userRepository.save(sourceAccount);

        // notify the creditor via email
        EmailDetails emailDetails = EmailDetails
                .builder()
                .subject(AccountUtils.ACCOUNT_DEBITED_SUCCESS_MESSAGE)
                .recipient(sourceAccount.getEmail())
                .messageBody(AccountUtils.ACCOUNT_DEBITED_SUCCESS_MESSAGE)
                .build();
        emailService.sendEmailAlert(emailDetails);

        // credit the destination account
        User destinationAccount = userRepository.findByAccountNumber(transferRequest.getDestinationAccountNumber());
        destinationAccount.setAccountBalance(destinationAccount.getAccountBalance().add(BigDecimal.valueOf(transferRequest.getAmount())));
        userRepository.save(destinationAccount);

        return BankResponse
                .builder()
                .responseCode(AccountUtils.ACCOUNT_TRANSFER_CODE)
                .responseMessage(AccountUtils.ACCOUNT_TRANSFER_SUCCESS_MESSAGE)
                .accountInfo(AccountInfo
                        .builder()
                        .accountCreationDate(sourceAccount.getCreatedAt())
                        .accountNumber(transferRequest.getSourceAccountNumber())
                        .accountBalance(sourceAccount.getAccountBalance())
                        .accountName(sourceAccount.getFirstName() + " " + sourceAccount.getLastName() + " " + sourceAccount.getOtherName())
                        .build())
                .build();
    }
}
