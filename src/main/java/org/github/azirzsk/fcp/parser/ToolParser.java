package org.github.azirzsk.fcp.parser;

import com.alibaba.fastjson2.JSON;
import lombok.extern.slf4j.Slf4j;
import org.github.azirzsk.fcp.annotation.Function;
import org.github.azirzsk.fcp.entity.FunctionEntity;
import org.github.azirzsk.fcp.entity.ToolEntity;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zhangshukun
 * @since 2024/11/25
 */
@Slf4j
public class ToolParser {

    private static final FunctionParser FUNCTION_PARSER = new FunctionParser();

    public String parse(Class<?> clazz) {
        log.info("开始解析'{}'中的Function", clazz);
        Method[] methods = clazz.getDeclaredMethods();
        if (methods.length == 0) {
            log.warn("'{}'中没有任何方法", clazz);
            throw new NullPointerException(clazz + "中没有任何方法");
        }
        List<ToolEntity> toolEntityList = new ArrayList<>();
        for (Method method : methods) {
            Function function = method.getAnnotation(Function.class);
            if (function == null) {
                continue;
            }
            FunctionEntity functionEntity = FUNCTION_PARSER.parse(method);
            ToolEntity toolEntity = new ToolEntity();
            toolEntity.setFunction(functionEntity);
            toolEntityList.add(toolEntity);
        }
        String res = JSON.toJSONString(toolEntityList);
        log.info("解析'{}'中的Function成功：{}", clazz.getSimpleName(), res);
        return res;
    }
}
