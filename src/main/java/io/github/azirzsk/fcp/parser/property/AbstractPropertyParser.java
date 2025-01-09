package io.github.azirzsk.fcp.parser.property;

import io.github.azirzsk.fcp.FCPConfiguration;
import io.github.azirzsk.fcp.annotation.Property;
import io.github.azirzsk.fcp.converter.Converter;
import io.github.azirzsk.fcp.parser.AbstractParser;
import io.github.azirzsk.fcp.utils.ParserUtils;

import java.util.Arrays;
import java.util.List;

/**
 * @author zhangshukun
 * @since 2025/1/9
 */
public abstract class AbstractPropertyParser<T, R> extends AbstractParser<T, R> {

    public AbstractPropertyParser(FCPConfiguration fcpConfiguration) {
        super(fcpConfiguration);
    }

    /**
     * 解析枚举值
     *
     * @param converterClass 配置在{@link Property}注解中的枚举值转换类
     * @param clazz          {@link Property}所标注的参数/属性对应的类
     * @return 枚举值列表
     */
    public List<Object> parseEnum(Class<? extends Converter> converterClass, Class<?> clazz) {
        if (converterClass != null) {
            return ParserUtils.parseEnum(converterClass);
        }
        Object[] enumConstants = clazz.getEnumConstants();
        return Arrays.asList(enumConstants);
    }
}
