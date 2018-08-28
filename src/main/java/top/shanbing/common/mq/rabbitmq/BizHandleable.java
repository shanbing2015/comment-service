package top.shanbing.common.mq.rabbitmq;


/**
 * 消息处理机接口类,用于业务代码和相关MQ操作解耦
 * 每个消息都有对应不同的handle实现，因此抽象出这个接口
 *
 * handle只写业务方法
 */
public interface BizHandleable {

    /**
     * 这里写业务处理逻辑
     * @param message 消息内容
     * @return 若声明消费者时关闭autoAck：返回true表示消息成功消费，返回false表示消息处理失败，会自动重新排队重试
     *          若声明消费者时开启autoAck，所有消息都会自动确认，这个返回值无效
     */
    boolean handleBiz(String message);

}
