package top.shanbing.mq.rabbitmq.constant;

/**
 * 统一定义队列的名称
 */
public interface RabbitMqConstant {

    String QUEUE_TEST = "queue_test";

    /**
     * 延迟路由名称
     */
    String DELAY_EXCHANGE_NAME = "delay_exchange";

    /**
     * 通用QOS
     */
    int COMMON_QOS = 5;
}
