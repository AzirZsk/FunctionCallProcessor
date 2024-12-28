package org.github.azirzsk.fcp.parser.property;

import lombok.extern.slf4j.Slf4j;
import org.github.azirzsk.fcp.annotation.Property;
import org.github.azirzsk.fcp.entity.PropertyEntity;
import org.github.azirzsk.fcp.parser.Parser;
import org.github.azirzsk.fcp.utils.ParserUtils;

import java.lang.reflect.Field;

/**
 * 根据属性上的{@link Property}注解，解析字段信息
 *
 * @author zhangshukun
 * @since 2024/12/26
 */
@Slf4j
public class FieldParser implements Parser<Field, PropertyEntity> {

    @Override
    public PropertyEntity parse(Field field) {
        if (log.isDebugEnabled()) {
            log.debug("解析字段：{}", field);
        }
        Property property = field.getAnnotation(Property.class);
        if (property == null) {
            if (log.isTraceEnabled()) {
                log.trace("字段没有@Property注解，不进行解析：{}", field);
            }
            return null;
        }
        PropertyEntity propertyEntity = new PropertyEntity()
                .setName(Property.USE_PARAM_NAME.equals(property.name()) ? field.getName() : property.name())
                .setDescription(property.desc())
                .setField(field)
                .setRequired(property.required());

        // 解析参数对应类型
        Class<?> type = property.type();
        if (Property.AutoType.class == type) {
            propertyEntity.setType(ParserUtils.parseType(field.getType()));
        } else {
            propertyEntity.setType(type.getSimpleName().toLowerCase());
        }

        // 解析参数枚举值
        if (property.enums() != Property.NoneConverter.class) {
            propertyEntity.setEnumList(ParserUtils.parseEnum(property.enums()));
        }
        if (log.isDebugEnabled()) {
            log.debug("字段解析完成");
        }
        return propertyEntity;
    }
}
