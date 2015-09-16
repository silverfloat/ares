package com.silverfloat.ares.orders.closing;

import com.silverfloat.ares.exchanges.ExchangeData;
import com.silverfloat.ares.exchanges.ExchangeSnapshot;
import com.silverfloat.ares.orders.filtering.PendingOrdersFilter;
import com.silverfloat.ares.orders.forwarding.OrdersForwarder;
import com.silverfloat.ares.orders.forwarding.TradeRequest;
import com.silverfloat.ares.orders.retrieving.PriceDataRetriver;
import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.dto.Order;
import com.xeiam.xchange.dto.trade.LimitOrder;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import static com.silverfloat.ares.orders.forwarding.TradeRequestBuilder.tradeRequest;

public class AresActionScheduler implements Runnable {


    private final PendingOrdersFilter pendingOrdersFilter;
    private final PriceDataRetriver priceDataRetriver;
    private final OrdersForwarder ordersForwarder;
    private final List<Exchange> exchanges;
    private final String marketName;

    private AresActionScheduler(PendingOrdersFilter pendingOrdersFilter, PriceDataRetriver priceDataRetriver, OrdersForwarder ordersForwarder, List<Exchange> exchanges, String marketName) {
        this.pendingOrdersFilter = pendingOrdersFilter;
        this.priceDataRetriver = priceDataRetriver;
        this.ordersForwarder = ordersForwarder;
        this.exchanges = exchanges;
        this.marketName = marketName;
    }

    public static AresActionScheduler orderCloser(PendingOrdersFilter pendingOrdersFilter, PriceDataRetriver priceDataRetriver, OrdersForwarder ordersForwarder, List<Exchange> exchanges, String marketName) {
        return new AresActionScheduler(pendingOrdersFilter, priceDataRetriver, ordersForwarder, exchanges, marketName);
    }

    @Override
    public void run() {
        try {
            final List<LimitOrder> pendingOrders = pendingOrdersFilter.filteredOrders();
            pendingOrders.parallelStream().forEach((order) -> {
                exchanges.stream().forEach(exchange -> {
                ExchangeData priceData = null;
                    try {
                        priceData = priceDataRetriver.latestPricesFromPricesCache(
                                exchange.getExchangeSpecification().getExchangeName(), marketName);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    closeOrder(exchange, order.getId());//TODO updatedOrder should return TradeRequest!!
                    ordersForwarder.forward(toTradeRequest(updatedOrder(order, priceData), exchange.getExchangeSpecification().getExchangeName()));
                });
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private TradeRequest toTradeRequest(LimitOrder limitOrder, String exchangeName) {
        return tradeRequest().withExchange(exchangeName).withQuantity(limitOrder.getTradableAmount())
                .withPrice(limitOrder.getLimitPrice()).withTradeType(limitOrder.getType().name()).build();
    }

    private LimitOrder updatedOrder(LimitOrder order, ExchangeData exchangeData) {
        final ExchangeSnapshot exchangeSnapshot = exchangeData.getExchangeSnapshot();
        final BigDecimal updatedPrice = order.getType() == Order.OrderType.ASK ? exchangeSnapshot.getBuyOrders().get(0).getPrice() : exchangeSnapshot.getSellOrders().get(0).getPrice();
        LimitOrder.Builder orderBuilder = new LimitOrder.Builder(order.getType(), order.getCurrencyPair());
        return orderBuilder.setTradableAmount(order.getTradableAmount()).
                setLimitPrice(updatedPrice).build();
    }

    private void closeOrder(Exchange exchange, String orderId) {
        try {
            exchange.getPollingTradeService().cancelOrder(orderId);
        } catch (IOException e) {
            throw new RuntimeException("An error occurred while cancelling the order: " + orderId, e);
        }
    }



}
