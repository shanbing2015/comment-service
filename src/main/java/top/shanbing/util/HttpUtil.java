package top.shanbing.util;

import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;

import java.util.List;

public class HttpUtil {

    public static String getIp(ServerHttpRequest request){
        HttpHeaders httpHeaders = request.getHeaders();
        List<String> list = httpHeaders.get("x-forwarded-for");
        if(list == null) return request.getRemoteAddress().getHostName();
        list.forEach( s-> System.out.println(s));
        return list.get(0);
    }
}
