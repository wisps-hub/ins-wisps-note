package com.wisps;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class RpcApplication {
    public static void main(String[] args) {
        SpringApplication.run(RpcApplication.class, args);
    }
}
