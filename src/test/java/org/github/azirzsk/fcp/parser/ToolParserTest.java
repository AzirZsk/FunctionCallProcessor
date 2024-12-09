package org.github.azirzsk.fcp.parser;

import org.github.azirzsk.fcp.annotation.Function;
import org.github.azirzsk.fcp.annotation.Property;
import org.github.azirzsk.fcp.parser.utils.FileUtils;
import org.github.azirzsk.fcp.parser.utils.JsonUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author zhangshukun
 * @since 2024/11/25
 */
public class ToolParserTest {

    @Test
    public void normalParser() {
        ToolParser toolParser = new ToolParser();
        String result = toolParser.parse(ToolTestClass.class);
        Object jsonObject = FileUtils.getJsonObject(ToolTestClass.class.getSimpleName());
        Assertions.assertEquals(JsonUtils.toJsonObject(result), jsonObject);
    }

    public static class ToolTestClass {

        @Function(desc = "计算最大公约数")
        public int gcd(@Property(desc = "非负整数A") int a, @Property(desc = "非负整数B") int b) {
            if (b == 0) {
                return a;
            }
            return gcd(b, a % b);
        }
    }
}
