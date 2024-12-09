package org.github.azirzsk.fcp.annotation;

import java.lang.annotation.*;

/**
 * 标注在方法上的注解，对应FunctionCall中的{@code function}
 *
 * @author zhangshukun
 * @since 2024/11/15
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Function {

    String USE_METHOD_NAME = "USE_METHOD_NAME";

    /**
     * 函数名称
     *
     * @return 函数名称
     */
    String name() default USE_METHOD_NAME;

    /**
     * 函数描述
     *
     * @return 函数描述
     */
    String desc();
}
