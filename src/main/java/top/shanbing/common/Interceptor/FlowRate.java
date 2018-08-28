package top.shanbing.common.Interceptor;

import top.shanbing.common.flowRate.FlowRateType;

import java.lang.annotation.*;

/**
 * 流量控制注解，在方法上加入注解进行该方法流量控制
 *      ip流量控制需要在方法参数中加入ServerHttpRequest对象，否则控制无效
 * @author shanbing
 * @date 2018/8/28.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface FlowRate{

    FlowRateType type() default FlowRateType.API_FLOWRATE;

    /**最大限流数*/
    int count() default 0;

    /**限流时间段（秒）*/
    int timeSlot() default 0;

}
