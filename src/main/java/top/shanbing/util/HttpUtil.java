package top.shanbing.util;

import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;

import java.net.InetSocketAddress;
import java.util.*;

public class HttpUtil {

    public static String getIp(ServerHttpRequest request){

        HttpHeaders httpHeaders = request.getHeaders();
        if(httpHeaders.containsKey("X-Forwarded-For")){
            List<String> list = httpHeaders.get("X-Forwarded-For");
            list.forEach( ip -> System.out.println("代理IP:"+ip));
            return list.get(0);
        }else{
            return request.getRemoteAddress().getAddress().getHostAddress();
        }

//        Set<Map.Entry<String, List<String>>> set  =  httpHeaders.entrySet();
//        Iterator<Map.Entry<String, List<String>>> iterator = set.iterator();
//        while(iterator.hasNext()){
//            Map.Entry<String, List<String>> entry = iterator.next();
//            String key = entry.getKey();
//            List<String> list = entry.getValue();
//            System.out.println("\n-------key:"+key+"----------");
//            list.forEach(str -> System.out.println("value:"+str));
//            System.out.println("--------------");
//        }
    }
}
