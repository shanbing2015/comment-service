package top.shanbing.mq.rabbitmq;

import com.rabbitmq.client.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import top.shanbing.mq.rabbitmq.constant.RabbitMqConstant;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;

/**
 * MQ消费者注册中心
 * @author shanbing
 * @date 2018/8/23.
 */
@Component
public class MqConsumerRegister {

    private static Logger logger = LoggerFactory.getLogger(MqConsumerRegister.class);

    @Autowired
    private ConnectionFactory connectFactory;

    private Connection connection;
    private ExecutorService es = newFixedThreadPoolWithQueueSize(32, 1024);

    @PostConstruct
    public void init() throws Exception {
        connection = connectFactory.newConnection();
        logger.info("MQ消费者注册中心已建立连接");
    }

    public static ExecutorService newFixedThreadPoolWithQueueSize(int poolSize, int queueLen) {
        return new ThreadPoolExecutor(poolSize, poolSize, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>(queueLen));
    }

    public void addNormalCounsumer(String taskName, MessageHandler handler, boolean isAutoAck, int qos) {
        try {
            Channel channel = connection.createChannel();
            channel.queueDeclare(taskName, true, false, false, null);

            channel.basicQos(qos);
            Consumer consumer = new DefaultConsumer(channel) {
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties,byte[] body) throws IOException {
                    String message = new String(body, "UTF-8");
                    try {
                        handler.handle(this.getChannel(), message, envelope.getDeliveryTag(), isAutoAck);
                    } catch (RejectedExecutionException e) {
                        if (!isAutoAck) {
                            channel.basicReject(envelope.getDeliveryTag(), true);
                        }
                        logger.info("Mq message handle queue full");
                    } catch (Exception e) {
                        logger.error("mq consume message error", e);
                    }
                }

                @Override
                public void handleShutdownSignal(String consumerTag, ShutdownSignalException sig) {
                    logger.error("consumer:" + consumerTag + "被关闭", sig);
                }
            };
            channel.basicConsume(taskName, isAutoAck, consumer);
        } catch (Exception e) {
            logger.error("mq consumer registe error", e);
        } finally {

        }
    }

    public void addDelayConsumer(String doneQueueName, String routingkey, MessageHandler handler, boolean autoAck,int qos) {
        try {
            // 声明延迟队列的exchange
            Channel channel = connection.createChannel();
            Map<String, Object> args = new HashMap();
            args.put("x-delayed-type", "direct");
            channel.exchangeDeclare(RabbitMqConstant.DELAY_EXCHANGE_NAME, "x-delayed-message", true, false, args);
            // 声明队列
            channel.queueDeclare(doneQueueName, true, false, false, null);
            // 绑定队列
            channel.queueBind(doneQueueName, RabbitMqConstant.DELAY_EXCHANGE_NAME, doneQueueName);
            channel.basicQos(qos);
            Consumer consumer = new DefaultConsumer(channel) {
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties,byte[] body) throws IOException {
                    String message = new String(body, "UTF-8");
                    try {
                        handler.handle(this.getChannel(), message, envelope.getDeliveryTag(), autoAck);
                    } catch (RejectedExecutionException e) {
                        if (!autoAck) {
                            // 只有没有开启ack时，才能重新入队列
                            channel.basicReject(envelope.getDeliveryTag(), true);
                        }
                        logger.info("Mq delay message handle queue full");
                    } catch (Exception e) {
                        logger.error("mq consumer message error", e);
                    }
                }

                @Override
                public void handleShutdownSignal(String consumerTag, ShutdownSignalException sig) {
                    logger.error("consumer:" + consumerTag + "被关闭", sig);
                }

            };
            channel.basicConsume(doneQueueName, autoAck, consumer);
        } catch (Exception e) {
            logger.error("mq consumer register error", e);
        }
    }

    /**
     * 队列满了后
     *
     * @param task
     */
    public void submitTask(Runnable task) {
        RejectedExecutionException ex = null;
        for (int i = 0; i < 2; i++) {
            try {
                es.submit(task);
                return;
            } catch (RejectedExecutionException e) {
                ex = e;
                try {
                    TimeUnit.MILLISECONDS.sleep(50);
                }catch (InterruptedException e1){
                }
            }
        }
        throw ex;
    }

    public void requeue(Channel channel, long deliveryTag) {
        try {
            channel.basicReject(deliveryTag, true);
        } catch (IOException e1) {
            logger.error("消息重新排队失败", e1);
        }
    }


    public interface MessageHandler {
        void handle(Channel channel, String message, long deliveryTag, boolean autoAck);
    }
}
