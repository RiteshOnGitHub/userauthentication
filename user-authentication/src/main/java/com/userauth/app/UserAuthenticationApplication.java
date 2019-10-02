package com.userauth.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.userauth.app.security.SecureAppProperties;

@SpringBootApplication
public class UserAuthenticationApplication extends SpringBootServletInitializer {
	
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application){
		return application.sources(UserAuthenticationApplication.class);
	}

	public static void main(String[] args) {
		SpringApplication.run(UserAuthenticationApplication.class, args);
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
