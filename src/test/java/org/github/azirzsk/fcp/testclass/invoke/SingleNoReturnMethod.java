package org.github.azirzsk.fcp.testclass.invoke;

import org.github.azirzsk.fcp.annotation.Function;
import org.github.azirzsk.fcp.annotation.Property;

/**
 * @author zhangshukun
 * @since 2024/12/22
 */
public class SingleNoReturnMethod {

    @Function(desc = "打印消息")
    public void printArg(@Property(desc = "消息内容") String message) {
        System.out.println(message);
    }
}
