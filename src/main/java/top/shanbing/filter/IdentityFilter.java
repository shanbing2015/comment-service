package top.shanbing.filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpCookie;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;
import top.shanbing.redis.IRedisManager;
import top.shanbing.redis.RedisKeys;
import top.shanbing.util.MD5;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 身份标识过滤器
 * @author shanbing
 * @date 2018/8/15.
 */
@Component
@Order(3)
public class IdentityFilter implements WebFilter {
    private static String key = "identity";
    private static int tt  = 60*60*24*30;//半个月有效期

    private static Map<String,Object> users = new ConcurrentHashMap<>();

    @Autowired
    private IRedisManager redisManager;

    @Override
    public Mono<Void> filter(ServerWebExchange serverWebExchange, WebFilterChain webFilterChain) {
        ServerHttpRequest request =  serverWebExchange.getRequest();
        ServerHttpResponse response =  serverWebExchange.getResponse();

        String domain = null;
        HttpHeaders httpHeaders = request.getHeaders();
        Set<Map.Entry<String, List<String>>> set  =  httpHeaders.entrySet();
        Iterator<Map.Entry<String, List<String>>> iterator = set.iterator();
        while(iterator.hasNext()){
            Map.Entry<String, List<String>> entry = iterator.next();
            String key = entry.getKey();
            if(key.equals("Origin")){
                domain = entry.getValue().get(0);
            }
        }

        MultiValueMap<String, HttpCookie> cookies =  request.getCookies();
        List<HttpCookie> list = cookies.get(key);
        Object identity = null;
        if(list !=null && list.get(0) != null){
            // todo 验证身份
            HttpCookie httpCookie = list.get(0);
            String value = httpCookie.getValue();
            System.out.println("携带cookie："+value);
            // todo 本地缓存+redis缓存
            identity = users.get(value);
            if(identity == null){
                identity = redisManager.get(RedisKeys.USER_IDENTIY+value,Object.class);
                if(identity != null){
                    users.put(value,new Object());
                }
            }
        }
        if(identity == null){
            // 创建身份
            String value = MD5.md5(Long.toString(new Date().getTime()));
            ResponseCookie.ResponseCookieBuilder cookieBuilder = ResponseCookie.from(key,value);
            if(domain != null){
                domain = domain.substring(domain.indexOf("."));
                System.out.println("domain:"+domain);
                cookieBuilder.domain(domain);
            }
            cookieBuilder.maxAge(tt);
            //cookieBuilder.httpOnly();
            //cookieBuilder.secure();
            cookieBuilder.path("/");
            ResponseCookie cookie = cookieBuilder.build();
            System.out.println("domain:"+cookie.getDomain());
            response.addCookie(cookie);
            // 身份redis缓存
            users.put(value,new Object());
            redisManager.setex(RedisKeys.USER_IDENTIY+value,tt,new Object(),false);
            System.out.println("创建身份："+ value);
        }
        return webFilterChain.filter(serverWebExchange);
    }
}
