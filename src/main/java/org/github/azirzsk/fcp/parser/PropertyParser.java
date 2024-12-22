package org.github.azirzsk.fcp.parser;

import lombok.extern.slf4j.Slf4j;
import org.github.azirzsk.fcp.annotation.Property;
import org.github.azirzsk.fcp.converter.Converter;
import org.github.azirzsk.fcp.entity.PropertyEntity;
import org.github.azirzsk.fcp.enums.PropertyType;

import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zhangshukun
 * @since 2024/11/17
 */
@Slf4j
public class PropertyParser implements Parser<Parameter, PropertyEntity> {

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
                log.trace("参数没有注解");
            }
            return null;
        }
        PropertyEntity propertyEntity = new PropertyEntity()
                .setName(Property.USE_PARAM_NAME.equals(property.name()) ? parameter.getName() : property.name())
                .setDescription(property.desc())
                .setParameter(parameter)
                .setRequired(property.required());

        // 解析参数对应类型
        Class<?> type = property.type();
        if (Property.AutoType.class == type) {
            propertyEntity.setType(parseType(parameter.getType()));
        } else {
            propertyEntity.setType(type.getSimpleName().toLowerCase());
        }

        // 解析参数枚举值
        if (property.enums() != Property.NoneConverter.class) {
            propertyEntity.setEnumList(parseEnum(property.enums()));
        }
        if (log.isTraceEnabled()) {
            log.trace("解析参数完成");
        }
        return propertyEntity;
    }

    /**
     * 解析参数枚举值
     *
     * @param clazz 枚举值转换类
     * @return 参数枚举值
     */
    public List<Object> parseEnum(Class<? extends Converter> clazz) {
        if (!clazz.isEnum()) {
            log.warn("参数使用了枚举值，但并没有继承枚举类：{}", clazz);
            throw new IllegalArgumentException("参数使用了枚举值，但并没有继承枚举类：" + clazz);
        }
        List<Object> res = new ArrayList<>();
        Converter[] enumConstants = clazz.getEnumConstants();
        for (Converter enumConstant : enumConstants) {
            res.add(enumConstant.convert());
        }
        return res;
    }

    /**
     * 解析参数对应类型
     *
     * @param type 类型
     * @return FunctionCall支持的属性类型
     */
    private String parseType(Class<?> type) {
        if (type.isArray() || type.isEnum()) {
            throw new IllegalArgumentException("不支持的类型：" + type);
        }
       return PropertyType.parse(type).getName();
    }

}
