package com.betasoftwares.banking_backend_services.utils;

import java.time.Year;

public class AccountUtils {

    public  static String ACCOUNT_EXIST_CODE = "001";
    public  static String ACCOUNT_EXIST_MESSAGE = "This user already has an account created";
    public  static String ACCOUNT_CREATION_SUCCESS = "This user already has an account created";
    public  static String ACCOUNT_CREATION_MESSAGE = "Account successfully created";

    public static String generateAccountNumber(){
        /**
         * Account Number generator
         */
        Year currentYear = Year.now();
        int min = 100000;
        int max = 999999;

        // generate a random number between min and max
        int randNumber = (int) Math.floor(Math.random() * (max -min + 1) +min);

        // convert currentYear and randNumber to string and concantinate them
        String year = String.valueOf(currentYear);
        String randomNumber = String.valueOf(randNumber);
        StringBuilder  accountNumber = new StringBuilder();
        return  accountNumber.append(year).append(randomNumber).toString();
    };

}
