package io.github.azirzsk.fcp.parser;

import io.github.azirzsk.fcp.FCPConfiguration;
import io.github.azirzsk.fcp.annotation.Function;
import io.github.azirzsk.fcp.entity.FunctionEntity;
import io.github.azirzsk.fcp.entity.ParametersEntity;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Method;

/**
 * @author zhangshukun
 * @since 2024/11/16
 */
@Slf4j
public class FunctionParser extends AbstractParser<Method, FunctionEntity> {

    public FunctionParser(FCPConfiguration fcpConfiguration) {
        super(fcpConfiguration);
    }

    public FunctionEntity parse(Method method) {
        if (log.isDebugEnabled()) {
            log.debug("开始解析Method:{}", method);
        }
        Function function = method.getAnnotation(Function.class);
        ParametersEntity parametersEntity = fcpConfiguration.getParameterParser().parse(method.getParameters());
        FunctionEntity res = new FunctionEntity()
                .setName(Function.USE_METHOD_NAME.equals(function.name()) ? method.getName() : function.name())
                .setDescription(function.desc())
                .setParameters(parametersEntity)
                .setMethod(method);
        if (log.isDebugEnabled()) {
            log.debug("解析Method成功：{}", res);
        }
        return res;
    }
}