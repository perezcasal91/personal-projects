package com.pizzashop.principal.configs;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
        info = @Info(
                description = "Rest API Pizza Shop using JWT Security and JPA",
                version = "V1.0",
                title = "Pizza Shop",
                contact = @Contact(
                        name = "Eliese Perez Casal",
                        email = "perezcasal91@gmail.com",
                        url = "https://github.com/perezcasal91"
                ),
                license = @License(
                        name = "Open Source",
                        url = "https://github.com/perezcasal91"
                ),
                termsOfService = "https://github.com/perezcasal91"
        ),
        externalDocs = @ExternalDocumentation(description = "Go to GitHub.",
                url = "https://github.com/tonybalde/Pizza-Web-Project/tree/back")
)
@SecurityScheme(
        name = "Bearer Authentication",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        scheme = "bearer"
)
@Configuration
public class SwaggerConfig {
}
