package top.shanbing.common.filter;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;
import top.shanbing.common.exception.GlobalExceptionHandler;
import top.shanbing.common.flowRate.GuavaRateLimiterFlowRate;
import top.shanbing.common.flowRate.RedisFlowRate;
import top.shanbing.domain.model.result.JsonResult;
import top.shanbing.util.HttpUtil;

/**
 * @author shanbing
 * @date 2018/8/6.
 * 接口限流filter
 */
//@Component
@Order(2)
public class FlowRateFilter  implements WebFilter {
    protected static Logger logger = LoggerFactory.getLogger(FlowRateFilter.class);

    @Autowired
    private GlobalExceptionHandler handler;

    @Autowired
    private RedisFlowRate redisFlowRate;

    @Autowired
    private GuavaRateLimiterFlowRate limiterFlowRate;

    @Override
    public Mono<Void> filter(ServerWebExchange serverWebExchange, WebFilterChain webFilterChain) {
        logger.info("FlowRateFilter");
        ServerHttpRequest request =  serverWebExchange.getRequest();
        ServerHttpResponse response =  serverWebExchange.getResponse();

        try {
            ipFlowRate(request);
            limiterFlowRate();
        }catch (Exception e){
            JsonResult jsonResult = handler.handler(e);
            String resultJson = JSONObject.toJSONString(jsonResult);
            return response.writeWith(Mono.just(response.bufferFactory().wrap(resultJson.getBytes())));
        }
        return webFilterChain.filter(serverWebExchange);
    }

    private void ipFlowRate(ServerHttpRequest request) throws Exception{
        String ip = HttpUtil.getIp(request);
        //redisFlowRate.acquire(ip);
    }

    private void limiterFlowRate() throws Exception{
        //limiterFlowRate.tryAcquire();
        limiterFlowRate.tryAcquire(1L);
    }
}
