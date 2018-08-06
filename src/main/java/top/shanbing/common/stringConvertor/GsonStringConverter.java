package top.shanbing.common.stringConvertor;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.lang.reflect.Type;

public class GsonStringConverter implements StringConverter{
      
    private Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
    

    public <T> String toString(T t) {
        if (t instanceof String) {
            return (String) t;
        }
        return gson.toJson(t);
    }

	public <T> T fromString(String value, Class<T> clazz) {
        if(clazz.isAssignableFrom(String.class)){
            return (T)value;
        }
        return gson.fromJson(value, clazz);
    }
    public <T> T fromString(String value, Type type) {
        return gson.fromJson(value, type); 
    }

}
