package org.github.azirzsk.fcp.parser;

/**
 * @author zhangshukun
 * @since 2024/12/22
 */
public interface Parser<T, R> {

    /**
     * 解析方法
     *
     * @param t 参数
     * @return 解析结果
     */
    R parse(T t);
}
