package top.shanbing.flowRate.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;
import top.shanbing.common.exception.BizException;
import top.shanbing.redis.IRedisManager;
import top.shanbing.redis.RedisKeys;
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

    @Value("${flowRate.maxCount}")
    private int defaultMaxCount;
    @Value("${flowRate.timeSlot}")
    private int defaultTimeSlot;    //秒
    @Value("${flowRate.ipBlock}")
    private boolean ipBlock;

    @PostConstruct
    private void init(){
        logger.info("RedisFlowRate限流已初始化，最大值:{}/{}s",defaultMaxCount,defaultTimeSlot);
    }


    /**
     *  通过ridis 进行访问次数限流 api限流
     * @param maxCount  最大次数
     * @param s 时间段内（秒）
     */
    public void apiAcquire(String apiName,int maxCount,int s){
        String key = String.format(RedisKeys.API_FLOW_RATE,apiName);
        if(maxCount == 0){
            maxCount = defaultMaxCount;
        }
        if(s ==0 ){
            s = defaultTimeSlot;
        }
        int count = redisManager.incr(key,(long)s).intValue();
        if(count > maxCount){
            logger.info("apiName:{},{}/{}s",apiName,count,s);
            throw new BizException(ErrorCodeEnum.API_FLOW_RATE);
        }
    }

    /**ip限流*/
    public void ipAcquire(String ip,int maxCount,int s){
        if(ip == null || ip.trim().length() == 0)
            throw new BizException(1,"ip错误,ip:"+ip);
        String key = String.format(RedisKeys.IP_FLOW_RATE,ip);
        if(maxCount == 0){
            maxCount = defaultMaxCount;
        }
        if(s ==0 ){
            s = defaultTimeSlot;
        }
        int count = redisManager.incr(ip,(long)s).intValue();
        if(count > maxCount){
            logger.info("ip:{},{}/{}s",key,count,s);
            ipBlock(ip,count,maxCount);
            throw new BizException(ErrorCodeEnum.IP_FLOW_RATE);
        }
    }

    /**APIip限流*/
    public void apiIpAcquire(String apiName,String ip,int maxCount,int s){
        if(ip == null || ip.trim().length() == 0)
            throw new BizException(1,"ip错误,ip:"+ip);
        String key = String.format(RedisKeys.API_IP_FLOW_RATE,apiName,ip);
        if(maxCount == 0){
            maxCount = defaultMaxCount;
        }
        if(s ==0 ){
            s = defaultTimeSlot;
        }
        int count = redisManager.incr(ip,(long)s).intValue();
        if(count > maxCount){
            logger.info("ip:{},{}/{}s",key,count,s);
            ipBlock(ip,count,maxCount);
            throw new BizException(ErrorCodeEnum.IP_FLOW_RATE);
        }
    }

    /**ip监控,加入黑名单策略*/
    @Async
    void ipBlock(String ip,int count,int maxCount){
        if(!ipBlock)
            return;
        int i = (count/maxCount);
        logger.info("ip:"+ip+",超过限定流量比例:"+i);
        Long tt;
        switch (i){
            case 1:{
                tt = blockService.addIpBlock(ip,1L); //1分钟
                break;
            }
            case 2:{
                tt = blockService.addIpBlock(ip,10L);
                break;
            }
            case 3:{
                tt = blockService.addIpBlock(ip,30L);
                break;
            }
            case 4:{
                tt = blockService.addIpBlock(ip,60L);
                break;
            }
            default:{
                tt = blockService.addIpBlockDay(ip);
                break;
            }
        }
        throw new BizException(ErrorCodeEnum.IP_FLOW_RATE,String.valueOf(tt)+"分钟内禁止调用");
    }

}
