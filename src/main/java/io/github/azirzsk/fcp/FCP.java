package io.github.azirzsk.fcp;

import com.alibaba.fastjson2.JSON;
import io.github.azirzsk.fcp.entity.ToolEntity;
import io.github.azirzsk.fcp.invoke.FunctionCall;
import io.github.azirzsk.fcp.parser.ToolParser;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * FunctionCall入口类
 * 生成JSON格式的工具调用以及回调对应方法
 *
 * @author zhangshukun
 * @since 2024/11/25
 */
@Slf4j
public class FCP {

    private final FCPConfiguration fcpConfiguration;

    private final ToolParser toolParser;

    private final FunctionCall functionCall = new FunctionCall();

    private final List<ToolEntity> toolEntityList = new ArrayList<>();

    private FCP(FCPConfiguration fcpConfiguration) {
        this.fcpConfiguration = fcpConfiguration;
        this.toolParser = new ToolParser(fcpConfiguration);
    }

    public static FCP create() {
        return new FCP(new FCPConfiguration());
    }

    /**
     * 解析大模型所需要的FunctionCall格式
     *
     * @param function 对象
     * @return FunctionCall
     */
    public String parse(Object function) {
        List<ToolEntity> parseResult = toolParser.parse(function.getClass());
        if (parseResult.isEmpty()) {
            log.warn("传入的对象中没有标注@Function注解的方法");
            throw new NullPointerException("传入的对象中没有标注@Function注解的方法");
        }
        // 构造函数名和实际方法的映射关系
        for (ToolEntity toolEntity : parseResult) {
            String name = toolEntity.getFunction().getName();
            functionCall.getMethodMap().put(name, toolEntity.getFunction());
        }
        functionCall.getObjectMap().put(function.getClass(), function);
        this.toolEntityList.addAll(parseResult);
        return JSON.toJSONString(parseResult);
    }

    /**
     * 获取当前已解析的FunctionCall
     *
     * @return FunctionCall
     */
    public String getAllFunctionCall() {
        return JSON.toJSONString(toolEntityList);
    }

    /**
     * 回调对应方法
     *
     * @param name         方法名称
     * @param argumentsStr 调用参数
     * @return 调用结果
     */
    public Object functionCall(String name, String argumentsStr) {
        return functionCall.functionCall(name, argumentsStr);
    }

}
