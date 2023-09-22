package com.losmessias.leherer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories("com.losmessias.leherer.repository")
public class LehererApplication {

	public static void main(String[] args) {
		SpringApplication.run(LehererApplication.class, args);
	}

}
