package com.silverfloat.ares;

import com.silverfloat.ares.configuration.Settings;
import com.silverfloat.ares.exchanges.Exchanges;
import com.silverfloat.ares.orders.filtering.*;
import com.silverfloat.ares.orders.forwarding.OrdersForwarder;
import com.silverfloat.ares.orders.forwarding.RemoteOrdersForwarder;
import com.silverfloat.ares.orders.replacing.PendingOrdersReplacer;
import com.silverfloat.ares.orders.replacing.ScheduledPendingOrdersReplacer;
import com.silverfloat.ares.orders.retrieving.PriceDataRetriver;
import com.silverfloat.ares.orders.retrieving.RemotePriceDataRetriver;
import org.glassfish.jersey.jackson.JacksonFeature;

import javax.ws.rs.client.WebTarget;
import java.io.IOException;
import java.util.Properties;

import static com.silverfloat.ares.configuration.PropertiesLoader.loadProperties;
import static com.silverfloat.ares.orders.closing.AresActionScheduler.orderCloser;
import static java.util.Arrays.asList;
import static javax.ws.rs.client.ClientBuilder.newClient;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON_TYPE;

public class Main {

    public static void main(String[] args) throws IOException {
        final Properties applicationProperties = loadProperties();
        Settings settings = new Settings(applicationProperties);
        Exchanges exchanges = new Exchanges(settings);
        PendingOrdersReplacer pendingOrdersReplacer = new ScheduledPendingOrdersReplacer(
        orderCloser(pendingOrdersFilter(exchanges, settings),priceDataRetriver(settings),
                orderForwarder(settings),exchanges.asAList(),"LTC_BTC"),settings);
        pendingOrdersReplacer.replacePendingOrders();
    }

    private static OrdersForwarder orderForwarder(Settings settings) {
        return new RemoteOrdersForwarder(rockefellerWebTarget(settings)
                .request(APPLICATION_JSON_TYPE));
    }

    private static WebTarget rockefellerWebTarget(Settings settings) {
        return newClient().
                register(JacksonFeature.class).
                target(settings.getRockefellerBaseUrl());
    }

    private static PriceDataRetriver priceDataRetriver(Settings settings) {
        return new RemotePriceDataRetriver(settings);
    }

    private static CustomPendingOrderFilter pendingOrdersFilter(Exchanges exchanges,  Settings settings) {
        return new CustomPendingOrderFilter(exchanges.asAList(), orderFilter(settings));
    }

    private static OrderFilter orderFilter(Settings settings) {
        final MinimumQuantityOrderFilter minimumQuantityOrderFilter = new MinimumQuantityOrderFilter(settings);
        final TimeElapsedOrderFilter timeElapsedOrderFilter = new TimeElapsedOrderFilter(settings);
        return new ComposableOrderFilter(asList(minimumQuantityOrderFilter, timeElapsedOrderFilter));
    }

}
