package com.example.security.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.List;

@Configuration
public class OpenAPIConfig {
    @Bean
    public OpenAPI userManagementOpenAPI() {
        Server devServer = new Server();
        devServer.setUrl("http://localhost:8080");
        devServer.setDescription("Development server");

        Contact contact = new Contact();
        contact.setEmail("rares.racsan@gmail.com");
        contact.setName("Rares Racsan");

        Info info = new Info()
                .title("User Management API")
                .version("1.0")
                .contact(contact)
                .description("REST API for managing users with CRUD operations");

        return new OpenAPI().info(info).servers(List.of(devServer));

    }
}