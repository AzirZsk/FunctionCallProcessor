package io.github.azirzsk.fcp.enums;

import io.github.azirzsk.fcp.annotation.Property;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * @author zhangshukun
 * @since 2024/12/22
 */
@Slf4j
@Getter
public enum PropertyType {

    STRING("string", "字符串", new Class<?>[]{String.class}),

    INTEGER("integer", "整型", new Class<?>[]{Integer.class, int.class}),

    DOUBLE("double", "双精度浮点型", new Class<?>[]{Double.class, double.class}),

    FLOAT("float", "单精度浮点型", new Class<?>[]{Float.class, float.class}),

    BOOLEAN("boolean", "布尔型", new Class<?>[]{Boolean.class, boolean.class}),

    OBJECT("object", "对象类型", new Class<?>[]{Object.class});;

    private final String name;

    private final String desc;

    private final Set<Class<?>> typeSet;

    PropertyType(String name, String desc, Class<?>[] types) {
        this.name = name;
        this.desc = desc;
        this.typeSet = new HashSet<>(Arrays.asList(types));
    }

    /**
     * 解析functionCall支持的类型
     *
     * @param type 类
     * @return 类对应的property的类
     */
    public static PropertyType parse(Class<?> type) {
        for (PropertyType value : values()) {
            if (value.typeSet.contains(type)) {
                return value;
            }
        }
        // 检查类中字段是否存在@Property注解。如果有一个，则代表要使用object类型
        Field[] declaredFields = type.getDeclaredFields();
        if (Arrays.stream(declaredFields)
                .anyMatch(field -> field.isAnnotationPresent(Property.class))) {
            return OBJECT;
        }
        log.warn("暂不支持该类型：{}，类中也没有标注@Property注解的字段", type);
        throw new IllegalArgumentException("暂不支持该类型：" + type);
    }

}
