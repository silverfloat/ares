package com.silverfloat.ares.orders.replacing;

import com.silverfloat.ares.configuration.Settings;
import com.silverfloat.ares.orders.closing.AresActionScheduler;

import java.io.IOException;
import java.util.concurrent.ScheduledExecutorService;

import static java.util.concurrent.Executors.newScheduledThreadPool;
import static java.util.concurrent.TimeUnit.SECONDS;

public class ScheduledPendingOrdersReplacer implements PendingOrdersReplacer {

    private final AresActionScheduler aresActionScheduler;
    private final Settings settings;
    private ScheduledExecutorService scheduledExecutorService;

    public ScheduledPendingOrdersReplacer(AresActionScheduler aresActionScheduler,
             Settings settings) {
        this.aresActionScheduler = aresActionScheduler;
        this.settings = settings;
        scheduledExecutorService = newScheduledThreadPool(1);
    }

    public void replacePendingOrders() throws IOException {
        scheduledExecutorService.schedule(aresActionScheduler, settings.getQueryExchangesDelay(), SECONDS);
    }




}
