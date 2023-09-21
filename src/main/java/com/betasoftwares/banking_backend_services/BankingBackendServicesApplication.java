package com.betasoftwares.banking_backend_services;

import io.github.cdimascio.dotenv.Dotenv;
import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Conditional;

@SpringBootApplication
@OpenAPIDefinition(
        info = @Info(title = "Banking Services Backend",
        description = "Backend Rest API Services",
        version = "v1.0",
        contact = @Contact(name = "BetaSoftware",
                email = "angeloobeta@hotmail.com",
                url = "https://github.com/angeloobeta/banking_backend_backend"),
        license = @License(name = "BetaSoftware Licence",
        url = "https://github.com/angeloobeta")),
        externalDocs = @ExternalDocumentation(description = "Banking Backend Services Documentation",
                url = "https://github.com/angeloobeta/banking_backend_backend")
)
public class BankingBackendServicesApplication {

    public static void main(String[] args) {
        SpringApplication.run(BankingBackendServicesApplication.class, args);
    }

}
