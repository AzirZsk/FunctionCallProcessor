package org.github.azirzsk.fcp;

import com.alibaba.fastjson2.JSON;
import lombok.extern.slf4j.Slf4j;
import org.github.azirzsk.fcp.entity.ToolEntity;
import org.github.azirzsk.fcp.invoke.FunctionCall;
import org.github.azirzsk.fcp.parser.ToolParser;

import java.util.List;

/**
 * @author zhangshukun
 * @since 2024/11/25
 */
@Slf4j
public class FCP {

    private final ToolParser toolParser = new ToolParser();

    private final FunctionCall functionCall = new FunctionCall();

    public static FCP create() {
        return new FCP();
    }

    private FCP() {

    }

    /**
     * 解析大模型所需要的FunctionCall格式
     *
     * @param function 对象
     * @return FunctionCall
     */
    public String functionCall(Object function) {
        List<ToolEntity> toolEntityList = toolParser.parse(function.getClass());
        if (toolEntityList.isEmpty()) {
            throw new NullPointerException("传入的对应没有Function注解");
        }
        // 构造函数名和实际方法的映射关系
        for (ToolEntity toolEntity : toolEntityList) {
            String name = toolEntity.getFunction().getName();
            functionCall.getMethodMap().put(name, toolEntity.getFunction());
        }
        functionCall.getObjectMap().put(function.getClass(), function);
        return JSON.toJSONString(toolEntityList);
    }

    public Object functionCall(String name, String argumentsStr) {
        return functionCall.functionCall(name, argumentsStr);
    }

}
