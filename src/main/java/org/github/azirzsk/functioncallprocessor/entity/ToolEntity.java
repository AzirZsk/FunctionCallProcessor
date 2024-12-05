package org.github.azirzsk.functioncallprocessor.entity;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.Data;

/**
 * @author zhangshukun
 * @since 2024/11/25
 */
@Data
public class ToolEntity {

    @JSONField(ordinal = 1)
    private String type = "function";

    @JSONField(ordinal = 2)
    private FunctionEntity function;
}
