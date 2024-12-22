package org.github.azirzsk.fcp.parser;

import org.github.azirzsk.fcp.entity.ToolEntity;
import org.github.azirzsk.fcp.testclass.tool.SingleReturnMethod;
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

}
