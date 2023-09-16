//package com.betasoftwares.banking_backend_services.config;
//
//import io.github.cdimascio.dotenv.Dotenv;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class EnvConfig {
//
//    public EnvConfig() {
//        // Load the .env file
//        Dotenv dotenv = Dotenv.configure().load();
//
//        // Access environment variables
//        String databaseUrl = dotenv.get("DATABASE_URL");
//        String databaseUsername = dotenv.get("DATABASE_USERNAME");
//        String databasePassword = dotenv.get("DATABASE_PASSWORD");
//
//        // Print the values for verification
//        System.out.println("DATABASE_URL: " + databaseUrl);
//        System.out.println("DATABASE_USERNAME: " + databaseUsername);
//        System.out.println("DATABASE_PASSWORD: " + databasePassword);
//
//        // Set environment variables as system properties
//        System.setProperty("env.spring.datasource.url", databaseUrl);
//        System.setProperty("env.spring.datasource.username", databaseUsername);
//        System.setProperty("env.spring.datasource.password", databasePassword);    }
//}
//
//
//
//
//
