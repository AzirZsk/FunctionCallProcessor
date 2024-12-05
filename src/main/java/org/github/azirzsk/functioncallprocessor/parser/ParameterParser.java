package org.github.azirzsk.functioncallprocessor.parser;

import lombok.extern.slf4j.Slf4j;
import org.github.azirzsk.functioncallprocessor.entity.ParametersEntity;
import org.github.azirzsk.functioncallprocessor.entity.PropertyEntity;

import java.lang.reflect.Parameter;
import java.util.*;

/**
 * @author zhangshukun
 * @since 2024/11/17
 */
@Slf4j
public class ParameterParser {

    private static final PropertyParser PROPERTY_PARSER = new PropertyParser();

    public ParametersEntity parse(Parameter[] parameters) {
        if (log.isDebugEnabled()) {
            log.debug("解析参数：{}", Arrays.toString(parameters));
        }
        Map<String, PropertyEntity> properties = new HashMap<>();
        List<String> requiredList = new ArrayList<>();
        for (Parameter parameter : parameters) {
            PropertyEntity property = PROPERTY_PARSER.parse(parameter);
            if (property == null) {
                continue;
            }
            properties.put(property.getName(), property);
            if (property.isRequired()) {
                requiredList.add(property.getName());
            }
        }
        ParametersEntity res = new ParametersEntity()
                .setRequired(requiredList)
                .setProperties(properties);
        if (log.isDebugEnabled()) {
            log.debug("解析参数完成：{}", res);
        }
        return res;
    }
}
