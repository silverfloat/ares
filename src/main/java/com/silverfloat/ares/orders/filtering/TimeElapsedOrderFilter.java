package com.silverfloat.ares.orders.filtering;

import com.silverfloat.ares.configuration.Settings;
import com.silverfloat.ares.orders.filtering.OrderFilter;
import com.xeiam.xchange.dto.trade.LimitOrder;

import static java.lang.System.currentTimeMillis;

public class TimeElapsedOrderFilter implements OrderFilter {

    private final Settings settings;

    public TimeElapsedOrderFilter(Settings settings) {
        this.settings = settings;
    }

    @Override
    public boolean test(LimitOrder limitOrder) {
        final long difference = currentTimeMillis() - limitOrder.getTimestamp().getTime();
        return difference >  settings.getPendingOrderExpirationTimeInSeconds() * 1000;
    }
}
