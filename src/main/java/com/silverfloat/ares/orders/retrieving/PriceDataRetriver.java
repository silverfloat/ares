package com.silverfloat.ares.orders.retrieving;

import com.silverfloat.ares.exchanges.ExchangeData;

public interface PriceDataRetriver {
    public ExchangeData latestPricesFromPricesCache(String exchangeName, String marketName) throws Exception;
}
