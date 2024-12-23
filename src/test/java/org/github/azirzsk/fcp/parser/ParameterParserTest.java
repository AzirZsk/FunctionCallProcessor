package org.github.azirzsk.fcp.parser;

import org.github.azirzsk.fcp.entity.ParametersEntity;
import org.github.azirzsk.fcp.testclass.ParameterTestClass;
import org.github.azirzsk.fcp.utils.FileUtils;
import org.github.azirzsk.fcp.utils.JsonUtils;
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
}
