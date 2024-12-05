package org.github.azirzsk.functioncallprocessor.entity;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * 函数中，参数的信息
 *
 * @author zhangshukun
 * @since 2024/11/17
 */
@Data
@Accessors(chain = true)
public class PropertyEntity {

    @JSONField(ordinal = 1)
    private String type;

    @JSONField(ordinal = 2)
    private String description;

    @JSONField(name = "enum", ordinal = 3)
    private List<Object> enumList;

    @JSONField(serialize = false)
    private boolean required;

    @JSONField(serialize = false)
    private String name;
}
