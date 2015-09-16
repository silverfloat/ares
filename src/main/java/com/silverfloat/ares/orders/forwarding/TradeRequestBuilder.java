package com.silverfloat.ares.orders.forwarding;

import java.math.BigDecimal;

public class TradeRequestBuilder {
    private String exchange;
    private String tradeType;
    private BigDecimal price;
    private BigDecimal quantity;
    private String currencyPair;

    public static TradeRequestBuilder tradeRequest() {
        return new TradeRequestBuilder();
    }

    public TradeRequest build() {
        return new TradeRequest(exchange, tradeType, price, quantity, currencyPair);
    }

    public TradeRequestBuilder withExchange(String exchange) {
        this.exchange = exchange;
        return this;
    }

    public TradeRequestBuilder withTradeType(String tradeType) {
        this.tradeType = tradeType;
        return this;
    }

    public TradeRequestBuilder withPrice(BigDecimal price) {
        this.price = price;
        return this;
    }

    public TradeRequestBuilder withQuantity(BigDecimal quantity) {
        this.quantity = quantity;
        return this;
    }

    public TradeRequestBuilder withCurrencyPair(String currencyPair) {
        this.currencyPair = currencyPair;
        return this;
    }
}