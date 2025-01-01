package org.github.azirzsk.fcp.testclass.invoke;

import org.github.azirzsk.fcp.annotation.Function;

/**
 * @author zhangshukun
 * @since 2024/12/22
 */
public class SingleNoArgMethod {

    @Function(desc = "没有请求参数")
    public Integer noArg() {
        return 123;
    }
}
