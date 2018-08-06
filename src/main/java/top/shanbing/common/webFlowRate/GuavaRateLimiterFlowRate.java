package top.shanbing.common.webFlowRate;

import com.google.common.util.concurrent.RateLimiter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @author shanbing
 * @date 2018/8/3.
 * web接口限流
 *  采用guava的RateLimiter,类似令牌桶算法，未获取到令牌阻塞
 */
@Component
public class GuavaRateLimiterFlowRate {
    private static Logger logger = LoggerFactory.getLogger(GuavaRateLimiterFlowRate.class);

    private static RateLimiter limiter = null;

    @Value("${rateLimiter}")
    private int rateLimiter;

    private GuavaRateLimiterFlowRate(){
    }

    @PostConstruct
    public void init(){
        this.limiter = RateLimiter.create(rateLimiter);
        logger.info("RateLimiter限流已初始化，每秒不超过{}个任务被提交",rateLimiter);
    }

    public static void acquire(){
        limiter.acquire(); // 请求RateLimiter, 超过permits会被阻塞
    }

}
