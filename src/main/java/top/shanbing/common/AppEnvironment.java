package top.shanbing.common;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AppEnvironment {


	//@Value("${deploy.env}")
	private String env = "prod";

	
	public  boolean isProd(){
		return "prod".equalsIgnoreCase(env);
	}
	
	public  boolean isDev(){
		return "dev".equalsIgnoreCase(env);
	}

	public  boolean isTest(){
		return "test".equalsIgnoreCase(env);
	}
}
