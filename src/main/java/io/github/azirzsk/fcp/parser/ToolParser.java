package io.github.azirzsk.fcp.parser;

import io.github.azirzsk.fcp.FCPConfiguration;
import io.github.azirzsk.fcp.annotation.Function;
import io.github.azirzsk.fcp.entity.FunctionEntity;
import io.github.azirzsk.fcp.entity.ToolEntity;
import io.github.azirzsk.fcp.utils.ParserUtils;
import lombok.extern.slf4j.Slf4j;

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

    private final FCPConfiguration fcpConfiguration;

    public ToolParser(FCPConfiguration fcpConfiguration) {
        this.fcpConfiguration = fcpConfiguration;
    }

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
            FunctionEntity functionEntity = fcpConfiguration.getFunctionParser().parse(method);
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
