package com.example.gatewaymanagment;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Gateways API", version = "1.0", description = "APIs for gateway management"))
public class GatewayManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(GatewayManagementApplication.class, args);
	}

}
