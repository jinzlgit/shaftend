package com.jyd.core.parse;

import java.text.DecimalFormat;

/**
 * @author Zhenlin Jin
 * @date 2021/8/12 21:27
 */
public abstract class AbstractIParsePolicy<T> implements IParsePolicy {
    protected static final Double ONE_HUNDRED = 100.0;
    protected static final Double ONE_THOUSAND = 1000.0;
    protected static final Double ONE_PERCENT = 0.01;
    protected static final DecimalFormat HEX_COMPLEMENT = new DecimalFormat("00000000");

    protected abstract T parseHexStrToObject(String content);
}
