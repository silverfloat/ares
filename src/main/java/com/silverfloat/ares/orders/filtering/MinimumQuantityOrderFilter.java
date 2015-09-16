package com.silverfloat.ares.orders.filtering;

import com.silverfloat.ares.configuration.Settings;
import com.xeiam.xchange.dto.trade.LimitOrder;

public class MinimumQuantityOrderFilter implements OrderFilter {

    private final Settings settings;

    public MinimumQuantityOrderFilter(Settings settings) {
        this.settings = settings;
    }

    @Override
    public boolean test(LimitOrder limitOrder) {
        return limitOrder.getTradableAmount().compareTo(settings.getMinimumTradableAmount()) > 0;
    }
}
