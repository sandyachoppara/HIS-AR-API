package com.his;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class HisArApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(HisArApiApplication.class, args);
	}

}
