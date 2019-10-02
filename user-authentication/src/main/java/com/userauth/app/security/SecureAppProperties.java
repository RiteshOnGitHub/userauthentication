package com.userauth.app.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

public class SecureAppProperties {
	
	@Autowired
	private Environment env;
	
	public String getTokenSecret(){
		return env.getProperty("tokenSecret");
	}

}
