package com.silverfloat.ares.orders.forwarding;


import com.silverfloat.ares.configuration.Settings;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.junit.Test;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import java.math.BigDecimal;

import static com.silverfloat.ares.configuration.PropertiesLoader.loadProperties;
import static com.silverfloat.ares.orders.forwarding.TradeRequestBuilder.tradeRequest;
import static javax.ws.rs.client.ClientBuilder.newClient;
import static javax.ws.rs.client.Entity.entity;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class RemoteOrdersForwarderTest {

    @Test
    public void shouldForwardOrders() throws Exception {
        Invocation.Builder invocationBuilder = mock(Invocation.Builder.class);
        RemoteOrdersForwarder remoteOrdersForwarder =
                new RemoteOrdersForwarder(rockefeller());
        remoteOrdersForwarder.forward(stubTradeRequest());
        verify(invocationBuilder).post(entity(stubTradeRequest(), APPLICATION_JSON));
    }



    private TradeRequest stubTradeRequest() {
     return tradeRequest().withCurrencyPair("LTC_BTC")
                .withPrice(new BigDecimal(0.001D)).withQuantity(new BigDecimal(0.001D))
                .withTradeType("ASK").withExchange("btce").build();
    }

    private Invocation.Builder rockefeller() {
        Settings settings = new Settings(loadProperties());
         return newClient().
                register(JacksonFeature.class).
                target(settings.getRockefellerBaseUrl()).request();
    }
}
