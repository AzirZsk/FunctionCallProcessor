package io.github.azirzsk.fcp.testclass;

import io.github.azirzsk.fcp.annotation.Property;

/**
 * @author zhangshukun
 * @since 2024/12/22
 */
public class ParameterTestClass {

    public void testParameterMethod(@Property(desc = "姓名") String name, @Property(desc = "年龄") int age) {

    }
}
