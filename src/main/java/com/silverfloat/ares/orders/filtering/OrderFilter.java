package com.silverfloat.ares.orders.filtering;

import com.xeiam.xchange.dto.trade.LimitOrder;

import java.util.function.Predicate;

public interface OrderFilter extends Predicate<LimitOrder> {
}
