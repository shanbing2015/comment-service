package top.shanbing.common.flowRate;

import com.google.common.util.concurrent.RateLimiter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import top.shanbing.common.exception.BizException;
import top.shanbing.domain.enums.ErrorCodeEnum;

import javax.annotation.PostConstruct;
import java.util.concurrent.TimeUnit;

/**
 * @author shanbing
 * @date 2018/8/3.
 * web接口限流
 *  采用guava的RateLimiter,类似令牌桶算法，未获取到令牌阻塞
 */
@Component
public class GuavaRateLimiterFlowRate {
    private static Logger logger = LoggerFactory.getLogger(GuavaRateLimiterFlowRate.class);

    @Value("${rateLimiter}")
    private int rateLimiter;

    private  RateLimiter limiter;

    @PostConstruct
    private void init(){
        limiter = RateLimiter.create(rateLimiter);
        logger.info("RateLimiter限流已初始化，每秒不超过{}个任务被提交",rateLimiter);
    }

    public void acquire(){
        limiter.acquire(); // 请求RateLimiter, 超过permits会被阻塞
    }

    public void tryAcquire(){
        tryAcquire(3L);
    }

    public void tryAcquire(Long s){
        if(!limiter.tryAcquire(s,TimeUnit.SECONDS)){
            throw new BizException(ErrorCodeEnum.API_FLOW_RATE);
        }
    }
}
