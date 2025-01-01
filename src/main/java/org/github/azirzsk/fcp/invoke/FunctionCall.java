package org.github.azirzsk.fcp.invoke;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.github.azirzsk.fcp.entity.FunctionEntity;
import org.github.azirzsk.fcp.entity.ParametersEntity;
import org.github.azirzsk.fcp.entity.PropertyEntity;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author zhangshukun
 * @since 2024/12/22
 */
@Slf4j
@Getter
public class FunctionCall {

    private final Map<Class<?>, Object> objectMap = new HashMap<>();

    private final Map<String, FunctionEntity> methodMap = new HashMap<>();

    /**
     * 根据方法名称以及参数执行具体方法
     *
     * @param name         方法名称
     * @param argumentsStr 执行参数
     * @return 执行结果
     */
    public Object functionCall(String name, String argumentsStr) {
        FunctionEntity function = methodMap.get(name);
        if (function == null) {
            log.warn("没有找到对应的方法：{}", name);
            throw new NullPointerException("没有找到对应的方法：" + name);
        }
        JSONObject argumentJson = JSON.parseObject(argumentsStr);
        Object[] arguments = parseArguments(argumentJson, function.getParameters());
        Method method = function.getMethod();
        method.setAccessible(true);
        Object object = objectMap.get(method.getDeclaringClass());
        try {
            return method.invoke(object, arguments);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 解析参数
     *
     * @param argumentJson 参数json
     * @param parameters   参数实体
     * @return 参数数组
     */
    private Object[] parseArguments(JSONObject argumentJson, ParametersEntity parameters) {
        Map<String, PropertyEntity> propertiesMap = parameters.getProperties();
        Collection<PropertyEntity> property = propertiesMap.values();
        // 根据方法中参数顺序排序
        List<PropertyEntity> sortedList = property.stream()
                .sorted(Comparator.comparingInt(PropertyEntity::getIndex))
                .collect(Collectors.toList());
        Object[] res = new Object[sortedList.size()];
        for (int i = 0; i < sortedList.size(); i++) {
            PropertyEntity propertyEntity = sortedList.get(i);
            Parameter parameter = propertyEntity.getParameter();
            if (!argumentJson.containsKey(parameter.getName())) {
                String message = parameter.getDeclaringExecutable().getName() + "." + parameter.getName();
                log.warn("回调方法时缺少参数：{}", message);
                throw new NullPointerException("缺少参数：" + message);
            }
            Object arg = argumentJson.getObject(parameter.getName(), parameter.getType());
            res[i] = arg;
        }
        return res;
    }
}
