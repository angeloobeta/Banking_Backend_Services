package com.betasoftwares.banking_backend_services.controllers;

import com.betasoftwares.banking_backend_services.dto.BankResponse;
import com.betasoftwares.banking_backend_services.dto.CreditDebitRequest;
import com.betasoftwares.banking_backend_services.dto.EnquiryRequest;
import com.betasoftwares.banking_backend_services.dto.UserRequest;
import com.betasoftwares.banking_backend_services.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private UserService userService;
    @PostMapping
     public BankResponse createAccount(@RequestBody UserRequest userRequest){
        return userService.createAccount(userRequest);
    }

    @GetMapping("/balanceEnquiry")
    public BankResponse balanceEnquiry(@RequestBody EnquiryRequest enquiryRequest){
        return  userService.balanceEnquiry(enquiryRequest);
    }

    @GetMapping("/nameEnquiry")
    public String nameEnquiry(@RequestBody EnquiryRequest enquiryRequest){
        return  userService.nameEnquiry(enquiryRequest);
    }

    @PostMapping("/creditAccount")
    public BankResponse creditAccount(@RequestBody CreditDebitRequest creditDebitRequest){
        return userService.creditAccount(creditDebitRequest);
    }

    @PostMapping("/debitAccount")
    public BankResponse debitAccount(@RequestBody CreditDebitRequest creditDebitRequest){
        return userService.debitAccount(creditDebitRequest);
    }
}
