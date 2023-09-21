package com.betasoftwares.banking_backend_services.controllers;

import com.betasoftwares.banking_backend_services.dto.*;
import com.betasoftwares.banking_backend_services.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController()
@CrossOrigin("*")
@RequestMapping("/api/user")
@Tag(name = "User Account Documentation APIs")
public class UserController {
    @Autowired
    private UserService userService;
    @PostMapping("/createAccount")
    @Operation(summary = "Create new user Account",
            description = "Create new user account with an account Id"
    )
    @ApiResponse(
            responseCode = "201",
            description = "Http Status Code 201 CREATED"
    )
     public BankResponse createAccount(@RequestBody UserRequest userRequest){
        return userService.createAccount(userRequest);
    }

    @GetMapping("/balanceEnquiry")
    @Operation(summary = "Check Account Balance",
            description = "Check the user account balance"
    )
    @ApiResponse(
            responseCode = "201",
            description = "Http Status Code 201 CREATED"
    )
    public BankResponse balanceEnquiry(@RequestBody EnquiryRequest enquiryRequest){
        return  userService.balanceEnquiry(enquiryRequest);
    }

    @GetMapping("/nameEnquiry")
    @Operation(summary = "Name Enquiry",
            description = "Make name enquiry about an account"
    )
    @ApiResponse(
            responseCode = "201",
            description = "Http Status Code 201 CREATED"
    )
    public String nameEnquiry(@RequestBody EnquiryRequest enquiryRequest){
        return  userService.nameEnquiry(enquiryRequest);
    }

    @PostMapping("/creditAccount")
    @Operation(summary = "Credit Account",
            description = "Credit the account"
    )
    @ApiResponse(
            responseCode = "201",
            description = "Http Status Code 201 CREATED"
    )
    public BankResponse creditAccount(@RequestBody CreditDebitRequest creditDebitRequest){
        return userService.creditAccount(creditDebitRequest);
    }

    @PostMapping("/debitAccount")
    @Operation(summary = "Debit Account",
            description = "Debit the account"
    )
    @ApiResponse(
            responseCode = "201",
            description = "Http Status Code 201 CREATED"
    )
    public BankResponse debitAccount(@RequestBody CreditDebitRequest creditDebitRequest){
        return userService.debitAccount(creditDebitRequest);
    }

    @PostMapping("/transfer")
    @Operation(summary = "Transfer",
            description = "Make transfer from source account to destination account"
    )
    @ApiResponse(
            responseCode = "201",
            description = "Http Status Code 201 CREATED"
    )
    public BankResponse transferTransaction(@RequestBody TransferRequest transferRequest){
        return userService.transfer(transferRequest);
    }
}
