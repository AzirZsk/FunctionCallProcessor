package io.github.azirzsk.fcp.parser;

import com.alibaba.fastjson2.JSON;
import io.github.azirzsk.fcp.FCPConfiguration;
import io.github.azirzsk.fcp.entity.ToolEntity;
import io.github.azirzsk.fcp.testclass.invoke.RepeatMethod;
import io.github.azirzsk.fcp.testclass.invoke.SingleReturnMethod;
import io.github.azirzsk.fcp.utils.FileUtils;
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
        ToolParser toolParser = new ToolParser(new FCPConfiguration());
        List<ToolEntity> result = toolParser.parse(SingleReturnMethod.class);
        Object jsonObject = FileUtils.getJsonObject(SingleReturnMethod.class.getSimpleName());
        Assertions.assertEquals(JSON.toJSONString(result), jsonObject.toString());
    }

    @Test
    public void testRepeatMethodNameParser() {
        ToolParser toolParser = new ToolParser(new FCPConfiguration());
        List<ToolEntity> result = toolParser.parse(RepeatMethod.class);
        Assertions.assertEquals("print", result.get(0).getFunction().getName());
        Assertions.assertEquals("print(String, int)", result.get(1).getFunction().getName());
    }

}
