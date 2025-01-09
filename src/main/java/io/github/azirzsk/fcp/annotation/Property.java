package io.github.azirzsk.fcp.annotation;

import io.github.azirzsk.fcp.converter.Converter;
import io.github.azirzsk.fcp.enums.PropertyType;
import io.github.azirzsk.fcp.parser.property.AbstractPropertyParser;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author zhangshukun
 * @since 2024/11/15
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.PARAMETER, ElementType.FIELD})
public @interface Property {

    String USE_PARAM_NAME = "USE_PARAM_NAME";

    String name() default USE_PARAM_NAME;

    String desc();

    /**
     * 参数对应类型
     *
     * @return 参数类型
     * @see PropertyType 枚举类
     * @see AbstractPropertyParser#parseEnum(Class, Class) 解析枚举类
     */
    Class<?> type() default AutoType.class;

    boolean required() default true;

    /**
     * 枚举值，需要为枚举类
     *
     * @return Function的属性枚举值
     * @see AbstractPropertyParser#parseEnum(Class, Class) 解析枚举类
     */
    Class<? extends Converter> enums() default NoneConverter.class;

    /**
     * 自动解析参数
     */
    class AutoType {
    }

    /**
     * 参数并非枚举值
     */
    enum NoneConverter implements Converter {
        ;

        @Override
        public Object convert() {
            return null;
        }

    }

}
