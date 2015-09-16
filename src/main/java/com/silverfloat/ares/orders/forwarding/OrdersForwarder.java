package com.silverfloat.ares.orders.forwarding;

public interface OrdersForwarder {
    void forward(TradeRequest tradeRequest);
}
