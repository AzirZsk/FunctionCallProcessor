package io.github.azirzsk.fcp.parser.property;

import io.github.azirzsk.fcp.FCPConfiguration;
import io.github.azirzsk.fcp.entity.PropertyEntity;
import io.github.azirzsk.fcp.testclass.property.FieldTestClass;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

/**
 * @author zhangshukun
 * @since 2024/12/26
 */
public class FieldParserTest {

    @Test
    public void testFieldParser() {
        FieldParser fieldParser = new FieldParser(new FCPConfiguration());
        PropertyEntity propertyEntity = fieldParser.parse(FieldTestClass.LOCATION_FIELD);

        PropertyEntity expect = new PropertyEntity();
        expect.setName("location");
        expect.setType("string");
        expect.setRequire(true);
        expect.setDescription("位置");
        expect.setField(FieldTestClass.LOCATION_FIELD);

        Assertions.assertEquals(expect, propertyEntity);
    }

    @Test
    public void testAssignTypeFieldParser() {
        FieldParser fieldParser = new FieldParser(new FCPConfiguration());
        PropertyEntity propertyEntity = fieldParser.parse(FieldTestClass.INT_STRING_FIELD);

        PropertyEntity expect = new PropertyEntity();
        expect.setName("intString");
        expect.setType("integer");
        expect.setRequire(true);
        expect.setDescription("整型字符串");
        expect.setField(FieldTestClass.INT_STRING_FIELD);

        Assertions.assertEquals(expect, propertyEntity);
    }

    @Test
    public void testAssignEnumFieldParser() {
        FieldParser fieldParser = new FieldParser(new FCPConfiguration());
        PropertyEntity propertyEntity = fieldParser.parse(FieldTestClass.VACATION_TYPE_FIELD);

        PropertyEntity expect = new PropertyEntity();
        expect.setName("vacationType");
        expect.setType("string");
        expect.setRequire(true);
        expect.setDescription("假期类型");
        expect.setField(FieldTestClass.VACATION_TYPE_FIELD);
        List<Object> enumList = Arrays.asList(FieldTestClass.VacationType.SICK_LEAVE.name(), FieldTestClass.VacationType.ANNUAL_LEAVE.name());
        expect.setEnumList(enumList);

        Assertions.assertEquals(expect, propertyEntity);
    }

    @Test
    public void testNoFieldAnnotationParser() {
        FieldParser fieldParser = new FieldParser(new FCPConfiguration());
        PropertyEntity propertyEntity = fieldParser.parse(FieldTestClass.NO_ANNOTATION_FIELD);

        Assertions.assertNull(propertyEntity);
    }
}
