package top.shanbing.mq.rabbitmq.consumer.handler;

import top.shanbing.mq.rabbitmq.BizHandleable;

/**
 * python通知
 * @author shanbing
 * @date 2018/8/23.
 */
public class PythonHandler implements BizHandleable {
    @Override
    public boolean handleBiz(String message) {
        return false;
    }
}
