package com.silverfloat.ares.orders.filtering;

import com.xeiam.xchange.dto.trade.LimitOrder;

import java.io.IOException;
import java.util.List;

public interface PendingOrdersFilter {

    public List<LimitOrder> filteredOrders() throws IOException;
}
