package io.github.azirzsk.fcp.parser.property;

import com.alibaba.fastjson2.JSON;
import io.github.azirzsk.fcp.FCPConfiguration;
import io.github.azirzsk.fcp.entity.PropertyEntity;
import io.github.azirzsk.fcp.testclass.EmptyClass;
import io.github.azirzsk.fcp.testclass.property.FieldTestClass;
import io.github.azirzsk.fcp.testclass.property.PropertyTestClass;
import io.github.azirzsk.fcp.utils.FileUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.Map;

/**
 * @author zhangshukun
 * @since 2024/12/28
 */
public class ObjectPropertyParserTest {

    @Test
    public void testNormalParser() {
        ObjectPropertyParser objectPropertyParser = new ObjectPropertyParser(new FCPConfiguration());
        Map<String, PropertyEntity> parse = objectPropertyParser.parse(FieldTestClass.class);

        Object fieldTest = FileUtils.getJsonObject("FieldTest");
        Assertions.assertEquals(fieldTest.toString(), JSON.toJSONString(parse));
    }

    @Test
    public void testEmptyClassParser() {
        ObjectPropertyParser objectPropertyParser = new ObjectPropertyParser(new FCPConfiguration());
        Map<String, PropertyEntity> parse = objectPropertyParser.parse(EmptyClass.class);

        Assertions.assertEquals(Collections.emptyMap(), parse);
    }

    @Test
    public void testNestObjectParser() {
        ObjectPropertyParser objectPropertyParser = new ObjectPropertyParser(new FCPConfiguration());
        Map<String, PropertyEntity> parse = objectPropertyParser.parse(PropertyTestClass.Home.class);

        Object jsonObject = FileUtils.getJsonObject("ObjectPropertyParserTest.testNestObjectParser");
        Assertions.assertEquals(jsonObject.toString(), JSON.toJSONString(parse));
    }
}
