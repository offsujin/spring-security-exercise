package com.inflearn.securityex1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = {"com.inflearn.securityex1.repository"})
@EntityScan(basePackages = {"com.inflearn.securityex1"})
public class SecurityEx1Application {

	public static void main(String[] args) {
		SpringApplication.run(SecurityEx1Application.class, args);
	}

}
