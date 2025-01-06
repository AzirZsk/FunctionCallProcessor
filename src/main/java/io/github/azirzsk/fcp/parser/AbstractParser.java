package io.github.azirzsk.fcp.parser;

import io.github.azirzsk.fcp.FCPConfiguration;
import lombok.Data;

/**
 * @author zhangshukun
 * @since 2025/1/6
 */
@Data
public abstract class AbstractParser<T, R> implements Parser<T, R> {

    protected FCPConfiguration fcpConfiguration;

    public AbstractParser(FCPConfiguration fcpConfiguration) {
        this.fcpConfiguration = fcpConfiguration;
    }
}
