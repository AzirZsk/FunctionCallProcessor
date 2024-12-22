package org.github.azirzsk.fcp.testclass.tool;

import org.github.azirzsk.fcp.annotation.Function;
import org.github.azirzsk.fcp.annotation.Property;

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

}
