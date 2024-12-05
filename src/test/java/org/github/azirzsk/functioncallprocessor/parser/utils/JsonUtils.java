package org.github.azirzsk.functioncallprocessor.parser.utils;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;

/**
 * @author zhangshukun
 * @since 2024/11/19
 */
public class JsonUtils {

    public static Object toJsonObject(Object object) {
        if (object instanceof String) {
            return JSON.parse((String) object);
        }
        return JSON.parseObject(JSON.toJSONString(object));
    }
}
