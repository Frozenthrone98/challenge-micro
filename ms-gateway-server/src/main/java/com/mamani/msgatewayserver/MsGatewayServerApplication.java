package com.mamani.msgatewayserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class MsGatewayServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsGatewayServerApplication.class, args);
	}

}
