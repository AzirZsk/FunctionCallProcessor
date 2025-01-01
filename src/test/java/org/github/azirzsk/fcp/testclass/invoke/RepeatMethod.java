package org.github.azirzsk.fcp.testclass.invoke;

import org.github.azirzsk.fcp.annotation.Function;
import org.github.azirzsk.fcp.annotation.Property;

/**
 * @author zhangshukun
 * @since 2025/1/1
 */
public class RepeatMethod {


    @Function(desc = "打印字符串")
    public String print(String str) {
        return str;
    }

    @Function(desc = "打印字符串")
    public String print(@Property(desc = "重复的字符串") String str, @Property(desc = "重复次数") int repeat) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < repeat; i++) {
            sb.append(str);
        }
        return sb.toString();
    }
}


