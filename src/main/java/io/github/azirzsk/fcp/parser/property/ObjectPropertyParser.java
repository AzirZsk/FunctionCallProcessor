package io.github.azirzsk.fcp.parser.property;

import io.github.azirzsk.fcp.FCPConfiguration;
import io.github.azirzsk.fcp.entity.PropertyEntity;
import io.github.azirzsk.fcp.parser.AbstractParser;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zhangshukun
 * @since 2024/12/26
 */
@Slf4j
public class ObjectPropertyParser extends AbstractPropertyParser<Class<?>, Map<String, PropertyEntity>> {

    public ObjectPropertyParser(FCPConfiguration fcpConfiguration) {
        super(fcpConfiguration);
    }

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
            PropertyEntity propertyEntity = fcpConfiguration.getFieldParser().parse(field);
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
