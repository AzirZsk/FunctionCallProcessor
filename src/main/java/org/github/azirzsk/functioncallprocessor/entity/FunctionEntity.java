package org.github.azirzsk.functioncallprocessor.entity;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author zhangshukun
 * @since 2024/11/16
 */
@Data
@Accessors(chain = true)
public class FunctionEntity {

    @JSONField(ordinal = 1)
    private String name;

    @JSONField(ordinal = 2)
    private String description;

    @JSONField(ordinal = 3)
    private ParametersEntity parameters;

    @JSONField(ordinal = 4)
    private boolean strict = true;
}
