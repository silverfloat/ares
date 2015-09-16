package com.silverfloat.ares.orders.filtering;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.dto.trade.LimitOrder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class CustomPendingOrderFilter implements PendingOrdersFilter {

    private final List<Exchange> exchanges;
    private final OrderFilter orderFilter;

    public CustomPendingOrderFilter(List<Exchange> exchanges, OrderFilter orderFilter) {
        this.exchanges = exchanges;
        this.orderFilter = orderFilter;
    }

    public List<LimitOrder> filteredOrders() throws IOException {
        List<LimitOrder> limitOrders = new ArrayList<>();
        exchanges.stream().forEach(exchange -> {
            try {
                limitOrders.addAll(exchange.getPollingTradeService()
                        .getOpenOrders().getOpenOrders().stream().filter(orderFilter).collect(toList()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        return limitOrders;
    }

}
