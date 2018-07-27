package top.shanbing.util;

import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;

import java.net.InetSocketAddress;
import java.util.List;

public class HttpUtil {

    public static String getIp(ServerHttpRequest request){
        HttpHeaders httpHeaders = request.getHeaders();
        List<String> list = httpHeaders.get("x-forwarded-for");
        InetSocketAddress inetSocketAddress = request.getRemoteAddress();
        if(list == null) return inetSocketAddress.getAddress().getHostAddress();
        list.forEach( s-> System.out.println(s));
        return list.get(0);
    }
}
