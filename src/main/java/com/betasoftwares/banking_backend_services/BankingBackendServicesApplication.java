package com.betasoftwares.banking_backend_services;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BankingBackendServicesApplication {

    public static void main(String[] args) {

        // Load the .env file
        Dotenv dotenv = Dotenv.load();

        // Access environment variables
        String databaseUrl = dotenv.get("DATABASE_URL");
        String databaseUsername = dotenv.get("DATABASE_USERNAME");
        String databasePassword = dotenv.get("DATABASE_PASSWORD");
        SpringApplication.run(BankingBackendServicesApplication.class, args);
    }

}
