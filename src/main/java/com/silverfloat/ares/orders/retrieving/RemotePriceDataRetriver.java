package com.silverfloat.ares.orders.retrieving;

import com.silverfloat.ares.configuration.Settings;
import com.silverfloat.ares.exchanges.ExchangeData;
import org.glassfish.jersey.jackson.JacksonFeature;

import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;

import static java.lang.String.format;
import static javax.ws.rs.client.ClientBuilder.newClient;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON_TYPE;

public class RemotePriceDataRetriver implements PriceDataRetriver {

    private final Settings settings;

    public RemotePriceDataRetriver(Settings settings) {
        this.settings = settings;
    }

    @Override
    public ExchangeData latestPricesFromPricesCache(String exchangeName, String marketName) throws Exception {
        final WebTarget caronteTarget = newClient().
                register(JacksonFeature.class).
                target(settings.getCaronteBaseUrl());
        final String[] currencies = marketName.split("_");
        final Invocation.Builder requestBuilder = caronteTarget.path(format("exchange/%s/data", exchangeName)).
                queryParam("baseSymbol", currencies[0].toUpperCase()).
                queryParam("counterSymbol", currencies[1].toUpperCase()).
                request(APPLICATION_JSON_TYPE);
        return requestBuilder.buildGet().submit(ExchangeData.class).get();
    }
}
