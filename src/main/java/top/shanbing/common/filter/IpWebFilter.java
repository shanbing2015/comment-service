package top.shanbing.common.filter;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;
import top.shanbing.common.exception.GlobalExceptionHandler;
import top.shanbing.domain.model.result.JsonResult;
import top.shanbing.util.CommentUtil;
import top.shanbing.util.HttpUtil;

/**
 * @author shanbing
 * @date 2018/8/3.
 * ip 限制
 */

@Component
@Order(2)
public class IpWebFilter implements WebFilter {
    protected static Logger logger = LoggerFactory.getLogger(IpWebFilter.class);

    @Autowired
    private GlobalExceptionHandler handler;
    /**
     *
     * @param serverWebExchange:用请求换回响应. 包含有成对的http请求对象ServerHttpRequest和http响应对象ServerHttpResponse.
     * @param webFilterChain
     * @return
     */
    @Override
    public Mono<Void> filter(ServerWebExchange serverWebExchange, WebFilterChain webFilterChain) {
        logger.info("CommentWebFilter");
        ServerHttpRequest request =  serverWebExchange.getRequest();
        ServerHttpResponse response =  serverWebExchange.getResponse();
        try {
            CommentUtil.isIpBlack(HttpUtil.getIp(request));
        }catch (Exception e){
            JsonResult jsonResult = handler.handler(e);
            String resultJson = JSONObject.toJSONString(jsonResult);
            return response.writeWith(Mono.just(response.bufferFactory().wrap(resultJson.getBytes())));
        }
        return webFilterChain.filter(serverWebExchange);
    }
}
