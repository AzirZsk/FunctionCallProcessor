package org.github.azirzsk.fcp.parser;

import org.github.azirzsk.fcp.entity.ToolEntity;
import org.github.azirzsk.fcp.testclass.invoke.RepeatMethod;
import org.github.azirzsk.fcp.testclass.invoke.SingleReturnMethod;
import org.github.azirzsk.fcp.utils.FileUtils;
import org.github.azirzsk.fcp.utils.JsonUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

/**
 * @author zhangshukun
 * @since 2024/11/25
 */
public class ToolParserTest {

    @Test
    public void normalParser() {
        ToolParser toolParser = new ToolParser();
        List<ToolEntity> result = toolParser.parse(SingleReturnMethod.class);
        Object jsonObject = FileUtils.getJsonObject(SingleReturnMethod.class.getSimpleName());
        Assertions.assertEquals(JsonUtils.toJsonObject(result), jsonObject);
    }

    @Test
    public void testRepeatMethodNameParser() {
        ToolParser toolParser = new ToolParser();
        List<ToolEntity> result = toolParser.parse(RepeatMethod.class);
        Assertions.assertEquals("print", result.get(0).getFunction().getName());
        Assertions.assertEquals("print(String, int)", result.get(1).getFunction().getName());
    }

}
