package top.shanbing;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import top.shanbing.common.SpringApplicationContext;

@SpringBootApplication
public class WebApp {
    public static void main(String[] args) {
        SpringApplicationContext.context = SpringApplication.run(WebApp.class,args);
    }
}

