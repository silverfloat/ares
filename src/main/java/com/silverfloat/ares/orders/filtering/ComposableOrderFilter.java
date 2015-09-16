package com.silverfloat.ares.orders.filtering;

import com.xeiam.xchange.dto.trade.LimitOrder;

import java.util.List;
import java.util.function.Predicate;

public class ComposableOrderFilter implements OrderFilter {

    private final List<Predicate<LimitOrder>> filters;

    public ComposableOrderFilter(List<Predicate<LimitOrder>> filters) {
        this.filters = filters;
    }

    @Override
    public boolean test(LimitOrder limitOrder) {
        return filters.stream().allMatch(filter -> filter.test(limitOrder));
    }
}
