package org.github.azirzsk.fcp.parser;

import lombok.extern.slf4j.Slf4j;
import org.github.azirzsk.fcp.annotation.Function;
import org.github.azirzsk.fcp.entity.FunctionEntity;
import org.github.azirzsk.fcp.entity.ParametersEntity;

import java.lang.reflect.Method;

/**
 * @author zhangshukun
 * @since 2024/11/16
 */
@Slf4j
public class FunctionParser {

    private static final ParameterParser PARAMETER_PARSER = new ParameterParser();

    public FunctionEntity parse(Method method) {
        if (log.isDebugEnabled()) {
            log.debug("开始解析Method:{}", method);
        }
        Function function = method.getAnnotation(Function.class);
        ParametersEntity parametersEntity = PARAMETER_PARSER.parse(method.getParameters());
        FunctionEntity res = new FunctionEntity()
                .setName(Function.USE_METHOD_NAME.equals(function.name()) ? method.getName() : function.name())
                .setDescription(function.desc())
                .setParameters(parametersEntity);
        if (log.isDebugEnabled()) {
            log.debug("解析Method成功：{}", res);
        }
        return res;
    }
}