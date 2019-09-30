package com.altemetric.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.altemetric.app.security.SecureAppProperties;

@SpringBootApplication
public class AltemetricAppApplication extends SpringBootServletInitializer {
	
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application){
		return application.sources(AltemetricAppApplication.class);
	}

	public static void main(String[] args) {
		SpringApplication.run(AltemetricAppApplication.class, args);
	}
	
	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder(){
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public SpringApplicationContext springApplicationContext(){
		return new SpringApplicationContext();
	}
	
	@Bean(name="SecureAppProperties")
	public SecureAppProperties getSecureAppProperties(){
		return new SecureAppProperties();
	}

}
