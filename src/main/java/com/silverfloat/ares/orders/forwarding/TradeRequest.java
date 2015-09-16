package com.silverfloat.ares.orders.forwarding;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public class TradeRequest {

    @JsonProperty("exchange")
    private String exchange;
    @JsonProperty("tradeType")
    private String tradeType;
    @JsonProperty("price")
    private BigDecimal price;
    @JsonProperty("quantity")
    private BigDecimal quantity;
    @JsonProperty("currencypair")
    private String currencyPair;

    public TradeRequest(String exchange, String tradeType, BigDecimal price, BigDecimal quantity, String currencyPair) {
        this.exchange = exchange;
        this.tradeType = tradeType;
        this.price = price;
        this.quantity = quantity;
        this.currencyPair = currencyPair;
    }

    public String getExchange() {
        return exchange;
    }

    public String getTradeType() {
        return tradeType;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public String getCurrencyPair() {
        return currencyPair;
    }

    @Override
    public String toString() {
        return "TradeRequest{" +
                "exchange='" + exchange + '\'' +
                ", tradeType='" + tradeType + '\'' +
                ", price=" + price +
                ", quantity=" + quantity +
                ", currencyPair='" + currencyPair + '\'' +
                '}';
    }
}