package com.mamani.mscustomermanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class MsCustomerManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsCustomerManagementApplication.class, args);
	}

}
