package com.silverfloat.ares.exchanges;

import java.util.List;

public class ExchangeSnapshot {

    private String exchangeName;
    private List<ExchangeOrder> buyOrders;
    private List<ExchangeOrder> sellOrders;

    private ExchangeSnapshot() {
    }

    private ExchangeSnapshot(String exchangeName, List<ExchangeOrder> buyOrders, List<ExchangeOrder> sellOrders) {
        this.exchangeName = exchangeName;
        this.buyOrders = buyOrders;
        this.sellOrders = sellOrders;
    }

    public static ExchangeSnapshot exchangeSnapshot(String exchangeName, List<ExchangeOrder> buyOrders, List<ExchangeOrder> sellOrders) {
        return new ExchangeSnapshot(exchangeName, buyOrders, sellOrders);
    }

    public String getExchangeName() {
        return exchangeName;
    }

    public List<ExchangeOrder> getBuyOrders() {
        return buyOrders;
    }

    public List<ExchangeOrder> getSellOrders() {
        return sellOrders;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ExchangeSnapshot that = (ExchangeSnapshot) o;

        if (buyOrders != null ? !buyOrders.equals(that.buyOrders) : that.buyOrders != null) return false;
        if (exchangeName != null ? !exchangeName.equals(that.exchangeName) : that.exchangeName != null) return false;
        if (sellOrders != null ? !sellOrders.equals(that.sellOrders) : that.sellOrders != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = exchangeName != null ? exchangeName.hashCode() : 0;
        result = 31 * result + (buyOrders != null ? buyOrders.hashCode() : 0);
        result = 31 * result + (sellOrders != null ? sellOrders.hashCode() : 0);
        return result;
    }
}
