package com.team2.fithub;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = "com.team2.fithub.model.dao")
public class FithubApplication {
	public static void main(String[] args) {
		SpringApplication.run(FithubApplication.class, args);
	}
}
