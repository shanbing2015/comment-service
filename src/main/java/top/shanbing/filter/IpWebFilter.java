package top.shanbing.filter;

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

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.Set;

/**
 * @author shanbing
 * @date 2018/8/3.
 * ip 限制
 */

//@Component
@Order(1)
public class IpWebFilter implements WebFilter {
    protected static Logger logger = LoggerFactory.getLogger(IpWebFilter.class);

    private Set<String> urlSet = new HashSet<>();
    @Autowired
    private GlobalExceptionHandler handler;

    @PostConstruct
    private void init(){
        // todo 可采用 注解标识需要限流的接口，这里再通过注解 初始化所有url 存放在list中
        urlSet.add("comment/v1/save");
        urlSet.add("comment/v1/list");
        logger.info("IP限流接口:"+urlSet.toString());
    }

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
        String urlPath = request.getURI().getPath();
        if(false && !urlSet.contains(urlPath)){
            return webFilterChain.filter(serverWebExchange);
        }
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
