package org.github.azirzsk.functioncallprocessor.parser.utils;

import com.alibaba.fastjson2.JSONObject;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author zhangshukun
 * @since 2024/11/18
 */
public class FileUtils {

    public static Object getJsonObject(String key) {
        JSONObject jsonObject = readJson("data.json");
        return jsonObject.get(key);
    }

    private static JSONObject readJson(String path) {
        try (InputStream resourceAsStream = FileUtils.class.getClassLoader().getResourceAsStream(path)) {
            byte[] bytes = new byte[resourceAsStream.available()];
            resourceAsStream.read(bytes);
            return JSONObject.parseObject(new String(bytes));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
