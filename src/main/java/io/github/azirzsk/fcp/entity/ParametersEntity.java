package io.github.azirzsk.fcp.entity;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;
import java.util.Map;

/**
 * @author zhangshukun
 * @since 2024/11/16
 */
@Data
@Accessors(chain = true)
public class ParametersEntity {

    @JSONField(ordinal = 1)
    private String type = "object";

    @JSONField(ordinal = 2)
    private Map<String, PropertyEntity> properties;

    @JSONField(ordinal = 3)
    private List<String> required;

    @JSONField(ordinal = 4)
    private boolean additionalProperties = false;
}
