package com.betasoftwares.banking_backend_services.utils;

import java.time.Year;

public class AccountUtils {

    public  static String ACCOUNT_EXIST_CODE = "001";
    public  static String ACCOUNT_EXIST_MESSAGE = "This user already has an account created";
    public  static String ACCOUNT_CREATION_SUCCESS = "002";
    public  static String ACCOUNT_CREATION_MESSAGE = "Account successfully created";
    public static String ACCOUNT_DOES_NOT_EXIST_CODE = "003";
    public  static String ACCOUNT_DOES_NOT_EXIST_MESSAGE = "User with the provided information does not exist";
    public  static String ACCOUNT_FOUND_CODE = "004";
    public  static String ACCOUNT_FOUND_SUCCESS = "User Account Found";
    public  static  String ACCOUNT_CREDITED_CODE = "005";
    public static String ACCOUNT_CREDITED_SUCCESS_MESSAGE = "Account has been credited successfully";

    public static String INSUFFICIENT_BALANCE_CODE = "006";
    public static String INSUFFICIENT_BALANCE_MESSAGE = "You don't have sufficient fund to perform the transaction";

    public static String ACCOUNT_DEBITED_CODE = "007";
    public static String ACCOUNT_DEBITED_SUCCESS_MESSAGE = "Account has been successfully debited";

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
        return year + randomNumber;
    }

}
