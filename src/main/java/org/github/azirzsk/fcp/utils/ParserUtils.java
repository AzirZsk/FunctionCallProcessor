package org.github.azirzsk.fcp.utils;

import lombok.extern.slf4j.Slf4j;
import org.github.azirzsk.fcp.converter.Converter;
import org.github.azirzsk.fcp.enums.PropertyType;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author zhangshukun
 * @since 2024/12/26
 */
@Slf4j
public class ParserUtils {

    /**
     * 解析方法名称
     * @param method 要解析的方法
     * @return 方法名称，例子：getUser(String, Integer)
     */
    public static String parseMethodName(Method method) {
        if (method == null) {
            throw new NullPointerException("方法为空");
        }
        String methodName = method.getName();
        Class<?>[] parameterTypes = method.getParameterTypes();
        List<String> parameterTypeStrList = Arrays.stream(parameterTypes)
                .map(Class::getSimpleName)
                .collect(Collectors.toList());
        return methodName + "(" + String.join(", ", parameterTypeStrList) + ")";
    }

    /**
     * 解析参数枚举值
     *
     * @param clazz 枚举值转换类
     * @return 参数枚举值
     */
    public static List<Object> parseEnum(Class<? extends Converter> clazz) {
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
    public static String parseType(Class<?> type) {
        if (type.isArray() || type.isEnum()) {
            throw new IllegalArgumentException("不支持的类型：" + type);
        }
        return PropertyType.parse(type).getName();
    }
}
