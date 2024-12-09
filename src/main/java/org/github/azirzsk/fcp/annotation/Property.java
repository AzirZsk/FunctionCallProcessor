package org.github.azirzsk.fcp.annotation;

import org.github.azirzsk.fcp.converter.Converter;

import java.lang.annotation.*;

/**
 * @author zhangshukun
 * @since 2024/11/15
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
public @interface Property {

    String USE_PARAM_NAME = "USE_PARAM_NAME";

    String name() default USE_PARAM_NAME;

    String desc();

    /**
     * 参数对应类型，支持类型
     * <pre>
     *     1. String
     *     2. Integer
     *     3. Double
     *     4. Boolean
     * </pre>
     *
     * @return 参数类型
     */
    Class<?> type() default AutoType.class;

    boolean required() default true;

    /**
     * 枚举值，需要为枚举类
     *
     * @return Function的属性枚举值
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
