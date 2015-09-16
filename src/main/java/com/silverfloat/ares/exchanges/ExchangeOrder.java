package com.silverfloat.ares.exchanges;

import java.math.BigDecimal;

import static com.silverfloat.ares.exchanges.BigDecimals.bigDecimal;

public class ExchangeOrder {

    private BigDecimal price;
    private BigDecimal quantity;

    private ExchangeOrder() {
    }

    private ExchangeOrder(BigDecimal price, BigDecimal quantity) {
        this.price = price;
        this.quantity = quantity;
    }

    public static ExchangeOrder order(String price, String quantity) {
        return order(bigDecimal(price), bigDecimal(quantity));
    }

    public static ExchangeOrder order(BigDecimal price, BigDecimal quantity) {
        return new ExchangeOrder(price, quantity);
    }

    public BigDecimal getPrice() {
        return price;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ExchangeOrder order = (ExchangeOrder) o;

        if (!price.equals(order.price)) return false;
        if (!quantity.equals(order.quantity)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = price.hashCode();
        result = 31 * result + quantity.hashCode();
        return result;
    }
}
