package org.github.azirzsk.fcp.testclass.invoke;

import org.github.azirzsk.fcp.annotation.Function;
import org.github.azirzsk.fcp.annotation.Property;
import org.github.azirzsk.fcp.testclass.property.PropertyTestClass;

/**
 * @author zhangshukun
 * @since 2024/12/22
 */
public class SingleReturnMethod {

    @Function(desc = "计算最大公约数")
    public int gcd(@Property(desc = "非负整数A") int a, @Property(desc = "非负整数B") int b) {
        if (b == 0) {
            return a;
        }
        return gcd(b, a % b);
    }

    @Function(desc = "打印用户信息")
    public String print(@Property(desc = "用户信息") PropertyTestClass.UserInfo user) {
        return String.format("姓名：%s，年龄：%s", user.getName(), user.getAge());
    }

    @Function(desc = "打印家庭信息")
    public String printHome(@Property(desc = "家庭信息") PropertyTestClass.Home home) {
        return String.format("地址：%s，用户信息：姓名：%s，年龄：%s", home.getAddress(),
                home.getUserInfo().getName(), home.getUserInfo().getAge());
    }

}
