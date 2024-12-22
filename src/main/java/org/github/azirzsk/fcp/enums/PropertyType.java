package org.github.azirzsk.fcp.enums;

import lombok.Getter;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * @author zhangshukun
 * @since 2024/12/22
 */
@Getter
public enum PropertyType {

    STRING("string", "字符串", new Class<?>[]{String.class}),

    INTEGER("integer", "整型", new Class<?>[]{Integer.class, int.class}),

    DOUBLE("double", "双精度浮点型", new Class<?>[]{Double.class, double.class}),

    FLOAT("float", "单精度浮点型", new Class<?>[]{Float.class, float.class}),

    BOOLEAN("boolean", "布尔型", new Class<?>[]{Boolean.class, boolean.class}),
    ;

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
        throw new IllegalArgumentException("暂不支持该类型：" + type);
    }

}
