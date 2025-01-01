package org.github.azirzsk.fcp.parser.property;

import lombok.extern.slf4j.Slf4j;
import org.github.azirzsk.fcp.entity.PropertyEntity;
import org.github.azirzsk.fcp.parser.Parser;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zhangshukun
 * @since 2024/12/26
 */
@Slf4j
public class ObjectPropertyParser implements Parser<Class<?>, Map<String, PropertyEntity>> {

    private static final FieldParser FIELD_PARSER = new FieldParser();

    @Override
    public Map<String, PropertyEntity> parse(Class<?> clazz) {
        if (log.isDebugEnabled()) {
            log.debug("开始解析类中的属性：{}", clazz);
        }
        Field[] fields = clazz.getDeclaredFields();
        if (fields.length == 0) {
            if (log.isDebugEnabled()) {
                log.debug("类中没有字段，不进行解析：{}", clazz);
            }
            return Collections.emptyMap();
        }
        Map<String, PropertyEntity> res = new HashMap<>();
        for (Field field : fields) {
            PropertyEntity propertyEntity = FIELD_PARSER.parse(field);
            if (propertyEntity == null) {
                continue;
            }
            res.put(propertyEntity.getName(), propertyEntity);
        }
        if (log.isDebugEnabled()) {
            log.debug("类解析完成：{}", clazz);
        }
        return res;
    }
}
