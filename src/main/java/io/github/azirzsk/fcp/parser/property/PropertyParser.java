package io.github.azirzsk.fcp.parser.property;

import io.github.azirzsk.fcp.annotation.Property;
import io.github.azirzsk.fcp.entity.PropertyEntity;
import io.github.azirzsk.fcp.enums.PropertyType;
import io.github.azirzsk.fcp.parser.Parser;
import io.github.azirzsk.fcp.utils.ParserUtils;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author zhangshukun
 * @since 2024/11/17
 */
@Slf4j
public class PropertyParser implements Parser<Parameter, PropertyEntity> {

    private static final ObjectPropertyParser OBJECT_PROPERTY_PARSER = new ObjectPropertyParser();

    /**
     * 解析参数属性
     *
     * @param parameter 方法的参数
     * @return Function中参数需要的属性
     */
    public PropertyEntity parse(Parameter parameter) {
        if (log.isTraceEnabled()) {
            log.trace("解析参数：{}", parameter);
        }
        Property property = parameter.getAnnotation(Property.class);
        if (property == null) {
            if (log.isTraceEnabled()) {
                log.trace("参数没有@Property注解，不进行解析：{}", parameter);
            }
            return null;
        }
        PropertyEntity propertyEntity = new PropertyEntity()
                .setName(Property.USE_PARAM_NAME.equals(property.name()) ? parameter.getName() : property.name())
                .setDescription(property.desc())
                .setParameter(parameter)
                .setRequire(property.required());

        // 解析参数对应类型
        Class<?> type = property.type();
        if (Property.AutoType.class == type) {
            propertyEntity.setType(ParserUtils.parseType(parameter.getType()));
        } else {
            propertyEntity.setType(type.getSimpleName().toLowerCase());
        }
        // object类型的property
        if (PropertyType.OBJECT.getName().equals(propertyEntity.getType())) {
            Map<String, PropertyEntity> innerProperties = OBJECT_PROPERTY_PARSER.parse(parameter.getType());
            propertyEntity.setProperties(innerProperties);
            // require
            List<String> innerRequiredList = new ArrayList<>();
            for (PropertyEntity innerProperty : innerProperties.values()) {
                if (innerProperty.isRequire()) {
                    innerRequiredList.add(innerProperty.getName());
                }
            }
            propertyEntity.setRequired(innerRequiredList);
        }
        // 解析参数枚举值
        if (property.enums() != Property.NoneConverter.class) {
            propertyEntity.setEnumList(ParserUtils.parseEnum(property.enums()));
        }
        // 参数合法性校验 type:object和枚举值不能同时存在
        if (PropertyType.OBJECT.getName().equals(propertyEntity.getType()) && propertyEntity.getEnumList() != null) {
            log.warn("参数类型为object时，不能同时设置枚举值。typeClass：{}，enumList：{}", type, propertyEntity.getEnumList());
            throw new IllegalArgumentException("参数类型为object时，不能同时设置枚举值");
        }
        if (log.isTraceEnabled()) {
            log.trace("解析参数完成");
        }
        return propertyEntity;
    }

}
