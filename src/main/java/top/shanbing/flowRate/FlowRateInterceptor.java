package top.shanbing.flowRate;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import top.shanbing.common.SpringApplicationContext;
import top.shanbing.flowRate.guavaRateLimiter.GuavaRateLimiterFlowRate;
import top.shanbing.flowRate.redis.RedisFlowRate;
import top.shanbing.util.HttpUtil;

import java.lang.reflect.Modifier;


/**
 * @author shanbing
 * @date 2018/8/28.
 * 流量限制aop拦截器
 */

@Component
@Aspect
public class FlowRateInterceptor {
    protected static Logger logger = LoggerFactory.getLogger(FlowRateInterceptor.class);

    @Value("${flowRate.devEnvironmentFlowRate}")
    protected boolean devEnvironmentFlowRate;

    @Autowired
    private RedisFlowRate redisFlowRate;

    @Autowired
    private GuavaRateLimiterFlowRate limiterFlowRate;

    /**前置通知：进入方法前进行流量控制*/
    @Before("@annotation(flowRate)")
    public void before(JoinPoint joinPoint, FlowRate flowRate){
        joinPointInfo(joinPoint);
        if(true || SpringApplicationContext.isProd()){
            ServerHttpRequest request = getServerHttpRequest(joinPoint);
            flowRate(joinPoint,flowRate,request);
        }else{
            logger.info("非生产环境不进行流量控制");
        }
    }

    private void flowRate(JoinPoint joinPoint,FlowRate flowRate,ServerHttpRequest request){
        String apiName = getApiName(joinPoint);
        int maxCount = flowRate.count();
        int timeSlot = flowRate.timeSlot();
        String ip = null;

        if(flowRate.type() == FlowRateAction.IP_FLOWRATE || flowRate.type() == FlowRateAction.APIIP_FLOWRATE || flowRate.type() == FlowRateAction.API_APIIP_FLOWRATE){
            if(request == null){
                logger.error("ip限流失败,限流方法中无request参数");
                return;
            }
            ip = HttpUtil.getIp(request);
        }
        switch (flowRate.type()){
            case IP_FLOWRATE:{
                redisFlowRate.ipAcquire(ip,maxCount,timeSlot);
                break;
            }
            case API_FLOWRATE:{
                redisFlowRate.apiAcquire(apiName,maxCount,timeSlot);
                break;
            }
            case APIIP_FLOWRATE:{
                redisFlowRate.apiIpAcquire(apiName,ip,maxCount,timeSlot);
                break;
            }
            case API_APIIP_FLOWRATE:{
                redisFlowRate.apiIpAcquire(apiName,ip,maxCount,timeSlot);
                redisFlowRate.apiAcquire(apiName,maxCount,timeSlot);
                break;
            }
        }
    }

    private ServerHttpRequest getServerHttpRequest(JoinPoint joinPoint){
        //获取传入目标方法的参数
        Object[] args = joinPoint.getArgs();
        for (int i = 0; i < args.length; i++) {
            if(args[i] instanceof ServerHttpRequest){
                return (ServerHttpRequest)args[i];
            }
        }
        return null;
    }

    private String getApiName(JoinPoint joinPoint){
        // 类名 + 方法名
        return joinPoint.getSignature().getDeclaringTypeName() +"."+ joinPoint.getSignature().getName();
    }

    private void joinPointInfo (JoinPoint joinPoint){
        System.out.println("目标方法名为:" + joinPoint.getSignature().getName());
        System.out.println("目标方法所属类的简单类名:" + joinPoint.getSignature().getDeclaringType().getSimpleName());
        System.out.println("目标方法所属类的类名:" + joinPoint.getSignature().getDeclaringTypeName());
        System.out.println("目标方法声明类型:" + Modifier.toString(joinPoint.getSignature().getModifiers()));
        //获取传入目标方法的参数
        Object[] args = joinPoint.getArgs();
        for (int i = 0; i < args.length; i++) {
            System.out.println("第" + (i+1) + "个参数为:" + args[i]);
        }
        System.out.println("被代理的对象:" + joinPoint.getTarget());
        System.out.println("代理对象自己:" + joinPoint.getThis());
    }
}
