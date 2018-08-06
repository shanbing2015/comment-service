package top.shanbing.common.webFlowRate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;
import top.shanbing.common.exception.BizException;
import top.shanbing.common.redis.IRedisManager;
import top.shanbing.common.redis.RedisKeys;
import top.shanbing.domain.enums.ErrorCodeEnum;
import top.shanbing.service.BlockService;

import javax.annotation.PostConstruct;

/**
 * @author shanbing
 * @date 2018/8/6.
 * 基于redis限流方案（设置有效时间key,接口访问key的值自增，限制值大小），自定义过多方案（拒绝）
 */
@Component
@EnableAsync
public class RedisFlowRate {
    protected static Logger logger = LoggerFactory.getLogger(RedisFlowRate.class);
    @Autowired
    private IRedisManager redisManager;
    @Autowired
    private BlockService blockService;

    @Value("${ipRateLimiter}")
    private Long ipRateLimiter;
    @Value("${ipRateLimiterecond}")
    private Long ipRateLimiterecond;    //秒

    @PostConstruct
    private void init(){
        logger.info("RedisFlowRate限流已初始化，单个ip/{}s不超过{}个任务被提交",ipRateLimiterecond,ipRateLimiter);
    }

    public void acquire(String ip){
        String key = String.format(RedisKeys.IP_FLOW_RATE,ip);
        long count = redisManager.incr(key,ipRateLimiterecond);
        if(count>ipRateLimiter){
            logger.info("ip:{},{}/{}s",ip,count,ipRateLimiterecond);
            ipMonitor(ip,count);
            throw new BizException(ErrorCodeEnum.IP_FLOW_RATE);
        }
    }

    /**ip监控*/
    @Async
    void ipMonitor(String ip,long count){
        int i = (int)(count/ipRateLimiter);
        switch (i){
            case 1:{
                blockService.addIpBlockTemp(ip,60); //1小时
                break;
            }
            case 2:
            case 3:
            case 4:
            case 5:{
                blockService.addIpBlockTemp(ip);
                break;
            }
            default:{
                blockService.addIpBlock(ip);
            }
        }
    }
}
