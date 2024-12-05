package org.github.azirzsk.functioncallprocessor.parser;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import org.github.azirzsk.functioncallprocessor.annotation.Property;
import org.github.azirzsk.functioncallprocessor.entity.ParametersEntity;
import org.github.azirzsk.functioncallprocessor.parser.utils.FileUtils;
import org.github.azirzsk.functioncallprocessor.parser.utils.JsonUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;

/**
 * @author zhangshukun
 * @since 2024/11/17
 */
public class ParameterParserTest {

    @Test
    public void testParameterParser() throws NoSuchMethodException {
        Class<ParameterTestClass> parameterTestClassClass = ParameterTestClass.class;
        Method testParameterMethod = parameterTestClassClass.getMethod("testParameterMethod", String.class, int.class);

        ParameterParser parameterParser = new ParameterParser();
        ParametersEntity parametersEntity = parameterParser.parse(testParameterMethod.getParameters());
        Object jsonObject = FileUtils.getJsonObject(ParameterParserTest.class.getSimpleName());
        Assertions.assertEquals(JsonUtils.toJsonObject(parametersEntity), jsonObject);
    }

    public static class ParameterTestClass {

        public void testParameterMethod(@Property(desc = "姓名") String name, @Property(desc = "年龄") int age) {

        }
    }
}
