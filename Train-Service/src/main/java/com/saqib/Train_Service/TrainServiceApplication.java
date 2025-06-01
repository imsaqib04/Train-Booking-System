package com.saqib.Train_Service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication
@ComponentScan(basePackages = "com.saqib")
@EnableFeignClients
@EnableDiscoveryClient
public class TrainServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run( TrainServiceApplication.class, args);
	}

}
