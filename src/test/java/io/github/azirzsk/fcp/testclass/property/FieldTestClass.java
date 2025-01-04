package io.github.azirzsk.fcp.testclass.property;

import io.github.azirzsk.fcp.annotation.Property;
import io.github.azirzsk.fcp.converter.Converter;

import java.lang.reflect.Field;

/**
 * @author zhangshukun
 * @since 2024/12/26
 */
public class FieldTestClass {

    public static final Field LOCATION_FIELD;
    public static final Field NO_ANNOTATION_FIELD;
    public static final Field INT_STRING_FIELD;
    public static final Field VACATION_TYPE_FIELD;


    static {
        try {
            NO_ANNOTATION_FIELD = FieldTestClass.class.getDeclaredField("noAnnotation");
            LOCATION_FIELD = FieldTestClass.class.getDeclaredField("location");
            INT_STRING_FIELD = FieldTestClass.class.getDeclaredField("intString");
            VACATION_TYPE_FIELD = FieldTestClass.class.getDeclaredField("vacationType");
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
    }

    private String noAnnotation;

    @Property(desc = "位置")
    private String location;

    @Property(desc = "整型字符串", type = Integer.class)
    private String intString;

    @Property(desc = "假期类型", enums = VacationType.class)
    private String vacationType;

    public enum VacationType implements Converter {
        SICK_LEAVE,
        ANNUAL_LEAVE;

        @Override
        public Object convert() {
            return name();
        }
    }
}
