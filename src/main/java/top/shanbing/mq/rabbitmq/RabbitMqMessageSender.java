package top.shanbing.mq.rabbitmq;

import com.rabbitmq.client.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;
import redis.clients.util.SafeEncoder;
import top.shanbing.common.stringConvertor.GsonStringConverter;
import top.shanbing.common.stringConvertor.StringConverter;
import top.shanbing.mq.rabbitmq.constant.RabbitMqConstant;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

/**
 * 消息发送封装类
 * @author shanbing
 * @date 2018/8/29.
 */
@Component
@EnableAsync
public class RabbitMqMessageSender {
    private static Logger logger = LoggerFactory.getLogger(RabbitMqMessageSender.class);

    @Autowired
    private ConnectionFactory connectFactory;

    private Connection connection;

    private StringConverter converter = new GsonStringConverter();

    @PostConstruct
    private void init() throws Exception {
        connection = connectFactory.newConnection();
    }

    public void syncSend(String taskQueueName, String objectJson) {
        Channel channel = null;
        try {
            channel = connection.createChannel();
            //String queue, boolean durable是否持久化, boolean exclusive是否排外的, boolean autoDelete 是否自动删除, Map<String, Object> arguments) 队列中的消息什么时候会自动被删除
            channel.queueDeclare(taskQueueName, true, false, false, null);
            channel.basicPublish("", taskQueueName, MessageProperties.PERSISTENT_TEXT_PLAIN, SafeEncoder.encode(objectJson));
        } catch (Exception e) {
            logger.error("MQ消息发送失败", e);
        } finally {
            closeChannel(channel);
        }
    }

    /**发送消息方法主体，默认异步发送消息*/
    @Async
    public void send(String taskQueueName, String objectJson) {
        syncSend(taskQueueName,objectJson);
    }

    @Async
    public void sendDelayMessage(String taskQueueName,String objectJson, long delayTimeInSecond) {
        Channel channel = null;
        try{
            channel = connection.createChannel();
            byte[] messageBodyBytes = objectJson.getBytes("UTF-8");
            Map<String, Object> headers = new HashMap<String, Object>();
            headers.put("x-delay", 1000 * delayTimeInSecond);
            AMQP.BasicProperties.Builder props = new AMQP.BasicProperties.Builder().headers(headers);
            channel.queueDeclare(taskQueueName, true, false, false, null);
            channel.basicPublish(RabbitMqConstant.DELAY_EXCHANGE_NAME, taskQueueName, props.build(), messageBodyBytes);
        }catch (Exception e){
            logger.error("消息发送失败", e);
        } finally {
            closeChannel(channel);
        }
    }

    /**发送消息方法，可以直接传Bean*/
    public <T> void send(String taskQueueName, T data) {
        String objectJson;
        //如果是基本类型，直接处理
        if (data.getClass().isPrimitive()) {
            objectJson = data.toString();
        } else {
            objectJson = converter.toString(data);
        }
        send(taskQueueName,objectJson);
    }

    /**发送延迟消息方法，可以直接传Bean*/
    public <T> void sendDelayMessage(String routingKey,  T data, long delayTimeInSecond) {
        String objectJson;
        //如果是基本类型，直接处理
        if (data.getClass().isPrimitive()) {
            objectJson = data.toString();
        } else {
            objectJson = converter.toString(data);
        }
        sendDelayMessage(routingKey,objectJson, delayTimeInSecond);
    }

    private void closeChannel(Channel channel) {
        if (channel != null) {
            try {
                channel.close();
            } catch (Exception e) {
                logger.error("MQ关闭channel失败", e);
            }
        }
    }
}
