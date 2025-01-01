package org.github.azirzsk.fcp.parser;

import lombok.extern.slf4j.Slf4j;
import org.github.azirzsk.fcp.entity.ParametersEntity;
import org.github.azirzsk.fcp.entity.PropertyEntity;
import org.github.azirzsk.fcp.parser.property.PropertyParser;

import java.lang.reflect.Parameter;
import java.util.*;

/**
 * @author zhangshukun
 * @since 2024/11/17
 */
@Slf4j
public class ParameterParser implements Parser<Parameter[], ParametersEntity> {

    private static final PropertyParser PROPERTY_PARSER = new PropertyParser();

    public ParametersEntity parse(Parameter[] parameters) {
        if (log.isDebugEnabled()) {
            log.debug("解析参数：{}", Arrays.toString(parameters));
        }
        Map<String, PropertyEntity> properties = new HashMap<>();
        List<String> requiredList = new ArrayList<>();
        for (int i = 0; i < parameters.length; i++) {
            Parameter parameter = parameters[i];
            PropertyEntity property = PROPERTY_PARSER.parse(parameter);
            if (property == null) {
                continue;
            }
            property.setIndex(i);
            properties.put(property.getName(), property);
            if (property.isRequire()) {
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
