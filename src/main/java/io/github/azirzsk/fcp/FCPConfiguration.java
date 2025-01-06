package io.github.azirzsk.fcp;

import io.github.azirzsk.fcp.parser.FunctionParser;
import io.github.azirzsk.fcp.parser.ParameterParser;
import io.github.azirzsk.fcp.parser.property.FieldParser;
import io.github.azirzsk.fcp.parser.property.ObjectPropertyParser;
import io.github.azirzsk.fcp.parser.property.PropertyParser;
import lombok.Data;

/**
 * @author zhangshukun
 * @since 2025/1/6
 */
@Data
public class FCPConfiguration {

    private FieldParser fieldParser = new FieldParser(this);

    private ObjectPropertyParser objectPropertyParser = new ObjectPropertyParser(this);

    private PropertyParser propertyParser = new PropertyParser(this);

    private FunctionParser functionParser = new FunctionParser(this);

    private ParameterParser parameterParser = new ParameterParser(this);
}
