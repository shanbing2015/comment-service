package top.shanbing.common.stringConvertor;

import java.lang.reflect.Type;

/**
 * java对象到字符串的转换接口
 */
public interface StringConverter{
    
    /**将java对象转化为字符串.*/
    <T> String toString(T t);

    /** fromString:将字符串转换为指定类型的对象,适用于非泛型类型*/
    <T> T fromString(String value, Class<T> clazz);
    
    /** fromString:将字符串转换为指定类型的对象,适用于泛型类型.*/
    <T> T fromString(String value, Type type);
    
}
