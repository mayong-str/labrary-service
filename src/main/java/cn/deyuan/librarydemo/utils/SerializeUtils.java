package cn.deyuan.librarydemo.utils;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * author: admin
 * date: 2025/12/11 15:54
 */
public class SerializeUtils {

    /**
     * 序列化对象
     * @param object 对象
     * @return 对象的JSON字符串形式
     */
    public static String serialize(Object object) {
        return JSONObject.toJSONString(object);
    }

    /**
     * 反序列化对象
     * @param json 对象的JSON字符串形式
     * @param clazz 对象的Class
     * @return 反序列化后的对象
     */
    public static <T> T deserialize(String json, Class<T> clazz) {
        return JSONObject.parseObject(json, clazz);
    }

    /**
     * 反序列列表对象
     * @param json 列表对象的字符串形式
     * @param clazz 列表对象的Class
     * @return 反序列化后的列表对象
     */
    public static <T> List<T> deserializeList(String json, Class<T> clazz) {
        return JSONArray.parseArray(json, clazz);
    }
}
