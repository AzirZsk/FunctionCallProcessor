package io.github.azirzsk.fcp.parser.property;

import io.github.azirzsk.fcp.FCPConfiguration;
import io.github.azirzsk.fcp.annotation.Property;
import io.github.azirzsk.fcp.converter.Converter;
import io.github.azirzsk.fcp.entity.PropertyEntity;
import io.github.azirzsk.fcp.enums.PropertyType;
import io.github.azirzsk.fcp.parser.AbstractParser;
import io.github.azirzsk.fcp.utils.ParserUtils;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 根据属性上的{@link Property}注解，解析字段信息
 *
 * @author zhangshukun
 * @since 2024/12/26
 */
@Slf4j
public class FieldParser extends AbstractPropertyParser<Field, PropertyEntity> {

    public FieldParser(FCPConfiguration fcpConfiguration) {
        super(fcpConfiguration);
    }

    @Override
    public PropertyEntity parse(Field field) {
        if (log.isDebugEnabled()) {
            log.debug("解析字段：{}", field);
        }
        Property property = field.getAnnotation(Property.class);
        if (property == null) {
            if (log.isDebugEnabled()) {
                log.debug("字段没有@Field注解，不进行解析：{}", field);
            }
            return null;
        }
        PropertyEntity propertyEntity = new PropertyEntity()
                .setName(Property.USE_PARAM_NAME.equals(property.name()) ? field.getName() : property.name())
                .setDescription(property.desc())
                .setField(field)
                .setRequire(property.required());

        // 解析参数对应类型
        Class<?> type = property.type();
        if (Property.AutoType.class == type) {
            propertyEntity.setType(ParserUtils.parseType(field.getType()));
        } else {
            propertyEntity.setType(type.getSimpleName().toLowerCase());
        }
        // object类型的property
        if (PropertyType.OBJECT.getName().equals(propertyEntity.getType())) {
            Map<String, PropertyEntity> innerProperties = fcpConfiguration.getObjectPropertyParser().parse(field.getType());
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
        if (property.enums() != Property.NoneConverter.class
                || PropertyType.ENUM.getName().equals(propertyEntity.getType())) {
            propertyEntity.setEnumList(parseEnum(property.enums(), field.getType()));
        }
        // 参数合法性校验 type:object和枚举值不能同时存在
        if (PropertyType.OBJECT.getName().equals(propertyEntity.getType()) && propertyEntity.getEnumList() != null) {
            log.warn("参数类型为object时，不能同时设置枚举值。innerProperties：{}，enumList：{}", propertyEntity.getProperties(), propertyEntity.getEnumList());
            throw new IllegalArgumentException("参数类型为object时，不能同时设置枚举值");
        }
        if (log.isDebugEnabled()) {
            log.debug("字段解析完成");
        }
        return propertyEntity;
    }


}
