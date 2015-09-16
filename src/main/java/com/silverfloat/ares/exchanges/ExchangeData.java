package com.silverfloat.ares.exchanges;

import java.util.UUID;

import static java.util.UUID.randomUUID;

public class ExchangeData {

    private final ExchangeSnapshot exchangeSnapshot;
    private final UUID id;

    private ExchangeData(ExchangeSnapshot exchangeSnapshot, UUID id) {
        this.exchangeSnapshot = exchangeSnapshot;
        this.id = id;
    }

    public static ExchangeData exchangeData(ExchangeSnapshot exchangeSnapshot) {
        return new ExchangeData(exchangeSnapshot, randomUUID());
    }

    public ExchangeSnapshot getExchangeSnapshot() {
        return exchangeSnapshot;
    }

    public UUID getId() {
        return id;
    }
}
