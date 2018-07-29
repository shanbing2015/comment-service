package top.shanbing.util;

import org.apache.commons.lang.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class HttpUtil {

    public static String getIp(ServerHttpRequest request){
        String IP;
        HttpHeaders httpHeaders = request.getHeaders();

        Set<Map.Entry<String, List<String>>> set  =  httpHeaders.entrySet();
        Iterator<Map.Entry<String, List<String>>> iterator = set.iterator();
        while(iterator.hasNext()){
            Map.Entry<String, List<String>> entry = iterator.next();
            String key = entry.getKey();
            List<String> list = entry.getValue();
            System.out.println("\n-------key:"+key+"----------");
            list.forEach(str -> System.out.println("value:"+str));
            System.out.println("--------------");
        }
        System.out.println(deviceType(request));

        if(httpHeaders.containsKey("X-Forwarded-For")){
            List<String> list = httpHeaders.get("X-Forwarded-For");
            list.forEach( ip -> System.out.println("代理IP:"+ip));
            IP = list.get(0);
        }else{
            IP = request.getRemoteAddress().getAddress().getHostAddress();
        }

        String userAgent = httpHeaders.get("User-Agent").get(0);
        System.out.println("IP:"+IP+",请求设备来源:"+userAgent);

        return IP;
    }

    public static String deviceType(ServerHttpRequest request){
        HttpHeaders httpHeaders = request.getHeaders();
        String userAgent = httpHeaders.get("User-Agent").get(0);
        if (StringUtils.isBlank(userAgent)) {
            return "无";
        }
        if(userAgent.contains("Windows")) {
            return "Windows";
        }else if(userAgent.contains("iPhone")) {
            return "iPhone";
        }else{
            return "未知";
        }
    }

    public static String htmlEncode(String source) {
        if (source == null) {
            return "";
        }
        String html = "";
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < source.length(); i++) {
            char c = source.charAt(i);
            switch (c) {
                case '<':
                    buffer.append("&lt;");
                    break;
                case '>':
                    buffer.append("&gt;");
                    break;
                case '&':
                    buffer.append("&amp;");
                    break;
                case '"':
                    buffer.append("&quot;");
                    break;
                case 10:
                case 13:
                    break;
                default:
                    buffer.append(c);
            }
        }
        html = buffer.toString();
        return html;
    }
}
