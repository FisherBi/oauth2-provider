package com.fisherbi.oauth2.provider;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class Oauth2ProviderApplication {

	public static void main(String[] args) {
		SpringApplication.run(Oauth2ProviderApplication.class, args);
	}
}
