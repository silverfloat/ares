package com.silverfloat.ares.exchanges;

import java.math.BigDecimal;

public class BigDecimals {

    public static final BigDecimal ZERO_ONE = bigDecimal("0.1");

    public static BigDecimal bigDecimal(String numberAsString) {
        return new BigDecimal(numberAsString);
    }
}
