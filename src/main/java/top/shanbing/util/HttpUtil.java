package top.shanbing.util;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import top.shanbing.common.exception.GlobalExceptionHandler;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class HttpUtil {
    private static Logger logger = LoggerFactory.getLogger(HttpUtil.class);

    public static String getIp(ServerHttpRequest request){
        String ip;
        HttpHeaders httpHeaders = request.getHeaders();

        Set<Map.Entry<String, List<String>>> set  =  httpHeaders.entrySet();
        Iterator<Map.Entry<String, List<String>>> iterator = set.iterator();
        while(iterator.hasNext()){
            Map.Entry<String, List<String>> entry = iterator.next();
            String key = entry.getKey();
            List<String> list = entry.getValue();
            logger.debug("\n-------key:"+key+"----------");
            list.forEach(str -> logger.debug("value:"+str));
        }
        logger.debug(deviceType(request));

        if(httpHeaders.containsKey("X-Forwarded-For")){
            List<String> list = httpHeaders.get("X-Forwarded-For");
            list.forEach( agentIp -> logger.debug("代理IP:"+agentIp));
            ip = list.get(0);
        }else{
            ip = request.getRemoteAddress().getAddress().getHostAddress();
        }

        String userAgent = httpHeaders.get("User-Agent").get(0);
        logger.info("IP:"+ip+",请求设备来源:"+userAgent);

        return ip;
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
