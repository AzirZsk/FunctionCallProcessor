package io.github.azirzsk.fcp.testclass.property;

import io.github.azirzsk.fcp.annotation.Property;
import lombok.Data;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

/**
 * @author zhangshukun
 * @since 2024/12/28
 */
public class PropertyTestClass {

    public static final Parameter NO_ANNOTATION_PARAMETER;
    public static final Parameter ANNOTATED_PARAMETER;
    public static final Parameter STRING_PARAMETER;
    public static final Parameter CUSTOM_OBJECT_PARAMETER;
    public static final Parameter HOME_PARAMETER;
    public static final Parameter RECURSION_PARAMETER;
    public static final Parameter VACATION_TYPE_PARAMETER;
    public static final Parameter OBJECT_ENUM_PARAMETER;

    static {
        Class<PropertyTestClass> propertyTestClassClass = PropertyTestClass.class;
        Method method = propertyTestClassClass.getMethods()[0];
        Parameter[] parameters = method.getParameters();
        NO_ANNOTATION_PARAMETER = parameters[0];
        ANNOTATED_PARAMETER = parameters[1];
        STRING_PARAMETER = parameters[2];
        CUSTOM_OBJECT_PARAMETER = parameters[3];
        HOME_PARAMETER = parameters[4];
        RECURSION_PARAMETER = parameters[5];
        VACATION_TYPE_PARAMETER = parameters[6];
        OBJECT_ENUM_PARAMETER = parameters[7];
    }

    public void test(String noAnnotation,
                     @Property(desc = "我有一个属性") String annotated,
                     @Property(desc = "整数string", type = int.class) Integer integer,
                     @Property(desc = "员工信息") UserInfo userInfo,
                     @Property(desc = "家") Home home,
                     @Property(desc = "递归") Recursion recursion,
                     @Property(desc = "假期类型", enums = FieldTestClass.VacationType.class)
                     String vacationType,
                     @Property(desc = "指定object", type = Object.class, enums = FieldTestClass.VacationType.class) String objectEnum) {

    }

    @Data
    public static class UserInfo {

        @Property(desc = "年龄", type = int.class)
        private String age;

        @Property(desc = "姓名")
        private String name;
    }

    @Data
    public static class Home {

        @Property(desc = "地址")
        private String address;

        @Property(desc = "用户信息")
        private UserInfo userInfo;
    }

    @Data
    public static class Recursion {

        @Property(desc = "描述")
        private String desc;

        @Property(desc = "递归")
        private Recursion recursion;
    }
}
