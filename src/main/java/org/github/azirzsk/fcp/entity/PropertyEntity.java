package org.github.azirzsk.fcp.entity;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.Data;
import lombok.experimental.Accessors;

import java.lang.reflect.Field;
import java.lang.reflect.Parameter;
import java.util.List;
import java.util.Map;

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

    /**
     * 嵌套的属性
     * 当且仅当type为object时，才会有值
     */
    @JSONField(ordinal = 3)
    private Map<String, PropertyEntity> properties;

    @JSONField(name = "enum", ordinal = 3)
    private List<Object> enumList;

    /**
     * 内嵌对应的必填的字段
     * 上一层级为object类型时，不为空
     */
    @JSONField(ordinal = 4)
    private List<String> required;

    @JSONField(serialize = false)
    private boolean require;

    @JSONField(serialize = false)
    private String name;

    @JSONField(serialize = false)
    private Integer index;

    /**
     * 对象实际对应的参数
     */
    @JSONField(serialize = false)
    private Parameter parameter;

    /**
     * 对象实际对应的字段
     * 上一层级为object类型时，不为空
     */
    @JSONField(serialize = false)
    private Field field;
}
