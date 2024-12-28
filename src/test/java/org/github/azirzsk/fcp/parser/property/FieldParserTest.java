package org.github.azirzsk.fcp.parser.property;

import org.github.azirzsk.fcp.entity.PropertyEntity;
import org.github.azirzsk.fcp.testclass.field.FieldTest;
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
        FieldParser fieldParser = new FieldParser();
        PropertyEntity propertyEntity = fieldParser.parse(FieldTest.LOCATION_FIELD);

        PropertyEntity expect = new PropertyEntity();
        expect.setName("location");
        expect.setType("string");
        expect.setRequired(true);
        expect.setDescription("位置");
        expect.setField(FieldTest.LOCATION_FIELD);

        Assertions.assertEquals(expect, propertyEntity);
    }

    @Test
    public void testAssignTypeFieldParser() {
        FieldParser fieldParser = new FieldParser();
        PropertyEntity propertyEntity = fieldParser.parse(FieldTest.INT_STRING_FIELD);

        PropertyEntity expect = new PropertyEntity();
        expect.setName("intString");
        expect.setType("integer");
        expect.setRequired(true);
        expect.setDescription("整型字符串");
        expect.setField(FieldTest.INT_STRING_FIELD);

        Assertions.assertEquals(expect, propertyEntity);
    }

    @Test
    public void testAssignEnumFieldParser() {
        FieldParser fieldParser = new FieldParser();
        PropertyEntity propertyEntity = fieldParser.parse(FieldTest.VACATION_TYPE_FIELD);

        PropertyEntity expect = new PropertyEntity();
        expect.setName("vacationType");
        expect.setType("string");
        expect.setRequired(true);
        expect.setDescription("假期类型");
        expect.setField(FieldTest.VACATION_TYPE_FIELD);
        List<Object> enumList = Arrays.asList(FieldTest.VacationType.SICK_LEAVE.name(), FieldTest.VacationType.ANNUAL_LEAVE.name());
        expect.setEnumList(enumList);

        Assertions.assertEquals(expect, propertyEntity);
    }

    @Test
    public void testNoFieldAnnotationParser() {
        FieldParser fieldParser = new FieldParser();
        PropertyEntity propertyEntity = fieldParser.parse(FieldTest.NO_ANNOTATION_FIELD);

        Assertions.assertNull(propertyEntity);
    }
}
