package io.github.azirzsk.fcp.parser.property;

import com.alibaba.fastjson2.JSON;
import io.github.azirzsk.fcp.FCPConfiguration;
import io.github.azirzsk.fcp.entity.PropertyEntity;
import io.github.azirzsk.fcp.testclass.property.FieldTestClass;
import io.github.azirzsk.fcp.testclass.property.PropertyTestClass;
import io.github.azirzsk.fcp.utils.FileUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

/**
 * @author zhangshukun
 * @since 2024/12/28
 */
public class PropertyParserTest {

    @Test
    public void testNoAnnotationParser() {
        PropertyParser propertyParser = new PropertyParser(new FCPConfiguration());
        PropertyEntity propertyEntity = propertyParser.parse(PropertyTestClass.NO_ANNOTATION_PARAMETER);

        Assertions.assertNull(propertyEntity);
    }

    @Test
    public void testNormalParser() {
        PropertyParser propertyParser = new PropertyParser(new FCPConfiguration());
        PropertyEntity propertyEntity = propertyParser.parse(PropertyTestClass.ANNOTATED_PARAMETER);

        PropertyEntity expect = new PropertyEntity();
        expect.setName("annotated");
        expect.setDescription("我有一个属性");
        expect.setRequire(true);
        expect.setType("string");
        expect.setParameter(PropertyTestClass.ANNOTATED_PARAMETER);
        Assertions.assertEquals(expect, propertyEntity);
    }

    @Test
    public void testEnumParser() {
        PropertyParser propertyParser = new PropertyParser(new FCPConfiguration());
        PropertyEntity propertyEntity = propertyParser.parse(PropertyTestClass.VACATION_TYPE_PARAMETER);

        PropertyEntity expect = new PropertyEntity();
        expect.setName("vacationType");
        expect.setDescription("假期类型");
        expect.setRequire(true);
        expect.setType("string");
        List<Object> enumList = Arrays.asList(FieldTestClass.VacationType.SICK_LEAVE.name(), FieldTestClass.VacationType.ANNUAL_LEAVE.name());
        expect.setEnumList(enumList);
        expect.setParameter(PropertyTestClass.VACATION_TYPE_PARAMETER);

        Assertions.assertEquals(expect, propertyEntity);
    }

    @Test
    public void testEnumObjectParser() {
        PropertyParser propertyParser = new PropertyParser(new FCPConfiguration());
        Assertions.assertThrowsExactly(IllegalArgumentException.class,
                () -> propertyParser.parse(PropertyTestClass.OBJECT_ENUM_PARAMETER), "参数类型为object时，不能同时设置枚举值");
    }

    @Test
    public void testAssignTypeParser() {
        PropertyParser propertyParser = new PropertyParser(new FCPConfiguration());
        PropertyEntity propertyEntity = propertyParser.parse(PropertyTestClass.STRING_PARAMETER);

        PropertyEntity expect = new PropertyEntity();
        expect.setName("integer");
        expect.setDescription("整数string");
        expect.setRequire(true);
        expect.setType("int");
        expect.setParameter(PropertyTestClass.STRING_PARAMETER);

        Assertions.assertEquals(expect, propertyEntity);
    }

    @Test
    public void testCustomObjectParser() {
        PropertyParser propertyParser = new PropertyParser(new FCPConfiguration());
        PropertyEntity propertyEntity = propertyParser.parse(PropertyTestClass.CUSTOM_OBJECT_PARAMETER);
        Object expect = FileUtils.getJsonObject("PropertyParserTest.testCustomObjectParser");

        Assertions.assertEquals(expect.toString(), JSON.toJSONString(propertyEntity));
    }

    @Test
    public void testNestCustomObjectParser() {
        PropertyParser propertyParser = new PropertyParser(new FCPConfiguration());
        PropertyEntity propertyEntity = propertyParser.parse(PropertyTestClass.HOME_PARAMETER);
        System.out.println(JSON.toJSONString(propertyEntity));
        Object expect = FileUtils.getJsonObject("PropertyParserTest.testNestCustomObjectParser");

        Assertions.assertEquals(expect.toString(), JSON.toJSONString(propertyEntity));
    }

    @Test
    public void testRecursionObjectParser() {
        PropertyParser propertyParser = new PropertyParser(new FCPConfiguration());
        Assertions.assertThrowsExactly(StackOverflowError.class, () -> propertyParser.parse(PropertyTestClass.RECURSION_PARAMETER));
    }

}
