package org.github.azirzsk.fcp.parser;

import lombok.extern.slf4j.Slf4j;
import org.github.azirzsk.fcp.annotation.Function;
import org.github.azirzsk.fcp.entity.FunctionEntity;
import org.github.azirzsk.fcp.entity.ToolEntity;
import org.github.azirzsk.fcp.utils.ParserUtils;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author zhangshukun
 * @since 2024/12/19
 */
@Slf4j
public class ToolParser {

    private static final FunctionParser FUNCTION_PARSER = new FunctionParser();

    public List<ToolEntity> parse(Class<?> clazz) {
        log.info("开始解析'{}'中的Function", clazz);
        Method[] methods = clazz.getDeclaredMethods();
        if (methods.length == 0) {
            log.warn("'{}'中没有任何方法", clazz);
            throw new NullPointerException(clazz + "中没有任何方法");
        }
        Set<String> functionNameSet = new HashSet<>();
        List<ToolEntity> toolEntityList = new ArrayList<>();
        for (Method method : methods) {
            Function function = method.getAnnotation(Function.class);
            if (function == null) {
                continue;
            }
            FunctionEntity functionEntity = FUNCTION_PARSER.parse(method);
            if (functionNameSet.contains(functionEntity.getName())) {
                log.warn("'{}'中存在重名的方法：{}", clazz, functionEntity.getName());
                String newFunctionName = ParserUtils.parseMethodName(method);
                functionEntity.setName(newFunctionName);
            }
            functionNameSet.add(functionEntity.getName());
            ToolEntity toolEntity = new ToolEntity();
            toolEntity.setFunction(functionEntity);
            toolEntityList.add(toolEntity);
        }
        log.info("解析'{}'中的Function成功：{}", clazz.getSimpleName(), toolEntityList);
        return toolEntityList;
    }
}
