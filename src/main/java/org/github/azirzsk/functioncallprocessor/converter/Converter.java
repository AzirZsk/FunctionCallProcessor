package org.github.azirzsk.functioncallprocessor.converter;

/**
 * @author zhangshukun
 * @since 2024/11/15
 */
public interface Converter {

    /**
     * 将枚举值转换成基础类型，支持类型：
     * <pre>
     * 1. String
     * 2. Integer
     * 3. Double
     * 4. Boolean
     * </pre>
     *
     * @return FunctionCall的枚举值
     */
    Object convert();


}
