package com.silverfloat.ares.orders.forwarding;

import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;

import static javax.ws.rs.client.Entity.entity;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON_TYPE;

public final class RemoteOrdersForwarder implements OrdersForwarder {

    private Invocation.Builder invocationBuilder;

    public RemoteOrdersForwarder(Invocation.Builder invocationBuilder) {
        this.invocationBuilder = invocationBuilder;
    }

    @Override
    public void forward(TradeRequest tradeRequest) {
        invocationBuilder.post(entity(tradeRequest, APPLICATION_JSON));
    }
}