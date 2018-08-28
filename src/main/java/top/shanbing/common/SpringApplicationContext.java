package top.shanbing.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * Created by shanbing.top on 2018/8/2.
 */
@Component
public class SpringApplicationContext {
    public static ConfigurableApplicationContext context;

    protected static Logger logger = LoggerFactory.getLogger(SpringApplicationContext.class);

    protected static String appEnvironment = "pro";

    @Value("${deploy.env}")
    private String env = "pro";

    @Value("${pageSize}")
    private int pageSize;

    private SpringApplicationContext(){
    }

    @PostConstruct
    private void init(){
        appEnvironment = env;
        logger.info("当前环境:"+appEnvironment);
        // 分页配置
        AppPage.pageSize = pageSize;
    }


    public static boolean isProd(){
        return "pro".equalsIgnoreCase(appEnvironment);
    }

    public static boolean isDev(){
        return "dev".equalsIgnoreCase(appEnvironment);
    }

    public static boolean isTest(){return "test".equalsIgnoreCase(appEnvironment);}
}
