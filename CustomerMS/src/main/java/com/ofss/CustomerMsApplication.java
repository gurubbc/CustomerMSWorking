package com.ofss;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableDiscoveryClient
public class CustomerMsApplication {

	public static void main(String[] args) {
		SpringApplication.run(CustomerMsApplication.class, args);
		System.out.println("Customer MS started....");
	}

	// Method level annotation
	// This should be automatically called when you start the application
	@Bean
	@LoadBalanced
	public RestTemplate getRestTemplate() {
		System.out.println("Returning an object of RestTemplate class");
		return new RestTemplate(); // this object must be created and kept by spring framework
	}
}
