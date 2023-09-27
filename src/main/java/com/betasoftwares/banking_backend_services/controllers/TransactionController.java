package com.betasoftwares.banking_backend_services.controllers;

import com.betasoftwares.banking_backend_services.entities.Transaction;
import com.betasoftwares.banking_backend_services.services.BankStatementService;
import com.itextpdf.text.DocumentException;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileNotFoundException;
import java.util.List;

@RestController
@RequestMapping("/bankStatement")
@AllArgsConstructor
public class TransactionController {
    private BankStatementService bankStatementService;

    @GetMapping()
    List<Transaction> generateBankStatement(@RequestParam String accountNumber, @RequestParam String startDate, @RequestParam String endDate) throws DocumentException, FileNotFoundException {
        return bankStatementService.allTransaction(accountNumber, startDate, endDate);
    }

}
