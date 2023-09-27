package com.betasoftwares.banking_backend_services.services;

import com.betasoftwares.banking_backend_services.dto.EmailDetails;
import com.betasoftwares.banking_backend_services.entities.Transaction;
import com.betasoftwares.banking_backend_services.entities.User;
import com.betasoftwares.banking_backend_services.repository.TransactionRepository;
import com.betasoftwares.banking_backend_services.repository.UserRepository;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Component @AllArgsConstructor
@Slf4j
public class BankStatementService {
    private TransactionRepository transactionRepository;
    private UserRepository userRepository;
    private EmailService emailService;
    /*
    Retrieve all transactions for a given account number within a given a date range
    generate a pdf file of the transactions
    send the generated pdf via email
     */

    public List<Transaction> allTransaction(String accountNumber, String startDate, String endDate) throws DocumentException, FileNotFoundException {
        LocalDate start = LocalDate.parse(startDate, DateTimeFormatter.ISO_DATE);
        LocalDate end = LocalDate.parse(endDate, DateTimeFormatter.ISO_DATE);
        User userDetail = userRepository.findByAccountNumber(accountNumber);

       List<Transaction> transactionsList = transactionRepository.findAll(accountNumber)
                .stream()
                .filter( transaction -> transaction.getAccountNumber().equals(accountNumber))
                .filter(transaction -> transaction.getCreatedAt().isEqual(start))
                .filter(transaction -> transaction.getCreatedAt().isEqual(end))
                .toList();
        // generate the account statement pdf report
        designStatement(transactionsList, userDetail, startDate, endDate);
        return transactionsList;
    }

    private final String FILE = "/Users/ifeanyichukwuobeta/IdeaProjects/SpringBoot/Banking Backend Services//accountStatement.pdf";

    private void designStatement(List<Transaction> transactionsList,
                                 User userDetail,
                                 String startDate,
                                 String endDate) throws FileNotFoundException, DocumentException {



        Rectangle statementSize = new Rectangle(PageSize.A4);
        Document document = new Document(statementSize);
        log.info("setting size of document");
        OutputStream outputStream = new FileOutputStream(FILE);
        PdfWriter.getInstance(document, outputStream);
        document.open();

        // create table header
        PdfPTable bankInfoTable = new PdfPTable(1);
        PdfPCell bankName = new PdfPCell(new Phrase("The Banking Backend Service"));
        bankName.setBorder(0);
        bankName.setBackgroundColor(BaseColor.BLUE);
        bankName.setPadding(20f);

        PdfPCell bankAddress = new PdfPCell(new Phrase("No 5A Obollo Road, Beach Junction"));
        bankAddress.setBorder(0);

        // Add the above implementation
        bankInfoTable.addCell(bankName);
        bankInfoTable.addCell(bankAddress);

        PdfPTable statementInfo = new PdfPTable(2);
        PdfPCell customerInfo = new PdfPCell(new Phrase("Start Date: " + startDate));
        customerInfo.setBorder(0);

        // transaction start date
        PdfPCell statement = new PdfPCell(new Phrase("STATEMENT OF ACCOUNT "));
        statementSize.setBorder(0);

        // transaction end date
        PdfPCell stopDate = new PdfPCell(new Phrase("End Date: " + endDate));
        statementSize.setBorder(0);

        // customer name
        PdfPCell name = new PdfPCell(new Phrase("Customer Name: "+
                userDetail.getFirstName() + " "
                + userDetail.getLastName() + " "
                + userDetail.getLastName()));
        name.setBorder(0);
        PdfPCell space = new PdfPCell();
        space.setBorder(0);

        // address
        PdfPCell address = new PdfPCell(new Phrase("Address: " + userDetail.getAddress()));
        address.setBorder(0);

        // populate the statement table
        statementInfo.addCell(customerInfo);
        statementInfo.addCell(statement);
        statementInfo.addCell(endDate);
        statementInfo.addCell(name);
        statementInfo.addCell(space);
        statementInfo.addCell(address);

        // generate the transaction table
        PdfPTable transactionsTable = new PdfPTable(4);
        // Date column
        PdfPCell date = new PdfPCell(new Phrase("DATE"));
        date.setBackgroundColor(BaseColor.BLUE);
        date.setBorder(0);
        // Transaction Type column
        PdfPCell transactionType = new PdfPCell(new Phrase("TRANSACTION TYPE"));
        transactionType.setBackgroundColor(BaseColor.BLUE);
        transactionType.setBorder(0);
        // Transaction Amount
        PdfPCell  transactionAmount = new PdfPCell(new Phrase("TRANSACTION AMOUNT"));
        transactionAmount.setBackgroundColor(BaseColor.BLUE);
        transactionAmount.setBorder(0);
        // Transaction Status
        PdfPCell  transactionStatus = new PdfPCell(new Phrase("TRANSACTION STATUS"));
        transactionStatus.setBackgroundColor(BaseColor.BLUE);
        transactionStatus.setBorder(0);

        // create the header for the table
        transactionsTable.addCell(date);
        transactionsTable.addCell(transactionType);
        transactionsTable.addCell(transactionAmount);
        transactionsTable.addCell(transactionStatus);
        // populate the transaction details
        transactionsList.forEach(transaction ->{
            transactionsTable.addCell( new Phrase(transaction.getCreatedAt().toString()));
            transactionsTable.addCell(new Phrase(transaction.getTransactionType()));
            transactionsTable.addCell(new Phrase(transaction.getAccountNumber()));
            transactionsTable.addCell(new Phrase(transaction.getTransactionStatus()));});

        // add the information to the document
        document.add(bankInfoTable);
        document.add(statementInfo);
        document.add(transactionsTable);

        // close the document
        document.close();

        EmailDetails emailDetails = EmailDetails.builder()
                .subject("STATEMENT OF ACCOUNT")
                .recipient(userDetail.getEmail())
                .messageBody("KINDLY FIND ATTACHED YOUR ACCOUNT STATEMENT")
                .attachment(FILE)
                .build();

        // send the attachment
        emailService.sendEmailWithAttachment(emailDetails);
    }


}
