package com.mamani.msaccountmanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class MsAccountManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsAccountManagementApplication.class, args);
	}

}
