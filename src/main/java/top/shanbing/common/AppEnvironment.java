package top.shanbing.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class AppEnvironment {
	protected static Logger logger = LoggerFactory.getLogger(AppEnvironment.class);

	@Value("${deploy.env}")
	private String env = "pro";

	@PostConstruct
	public void init(){
		logger.info("当前环境:"+env);
	}

	
	public  boolean isProd(){
		return "pro".equalsIgnoreCase(env);
	}
	
	public  boolean isDev(){
		return "dev".equalsIgnoreCase(env);
	}

	public  boolean isTest(){
		return "test".equalsIgnoreCase(env);
	}
}
