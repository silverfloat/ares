package com.silverfloat.ares.orders.closing;

import com.silverfloat.ares.configuration.Settings;
import com.silverfloat.ares.orders.filtering.PendingOrdersFilter;
import com.silverfloat.ares.orders.forwarding.OrdersForwarder;
import com.silverfloat.ares.orders.forwarding.TradeRequest;
import com.silverfloat.ares.orders.forwarding.TradeRequestBuilder;
import com.silverfloat.ares.orders.retrieving.PriceDataRetriver;
import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeFactory;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.btce.v3.BTCEExchange;
import com.xeiam.xchange.dto.trade.LimitOrder;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import static com.silverfloat.ares.orders.closing.AresActionScheduler.orderCloser;
import static org.mockito.Mockito.*;

public class AresActionSchedulerTest {

    private OrdersForwarder ordersForwarder = mock(OrdersForwarder.class);
    private PendingOrdersFilter pendingOrdersFilter = mock(PendingOrdersFilter.class);
    private PriceDataRetriver priceDataRetriver = mock(PriceDataRetriver.class);
    private AresActionScheduler aresActionScheduler;


    @Before
    public void pendingOrderFilterExpectations() throws IOException {
        when(pendingOrdersFilter.filteredOrders()).thenReturn(new ArrayList<LimitOrder>() {{
            add(null); //TODO stub some limit orders for the test
        }});
    }


    @Test
    public void shouldCloseTradeAndForwardToTheReplacer() {
        aresActionScheduler = orderCloser(pendingOrdersFilter, priceDataRetriver,
                ordersForwarder, exchangesWithPendingOrders(), "LTC_BTC");
        aresActionScheduler.run();
        verify(ordersForwarder).forward(tradeRequestExpectation());
    }

    private TradeRequest tradeRequestExpectation() {
        return TradeRequestBuilder.tradeRequest()
                .withTradeType("ASK")
                .withExchange("btce")
                .withPrice(new BigDecimal(0.01D))
                .withQuantity(new BigDecimal(0.01D))
                .withCurrencyPair("LTC_BTC").build();
    }

    private List<Exchange> exchangesWithPendingOrders() {
            Settings settings = new Settings(new Properties());
            final BTCEExchange btceExchange = new BTCEExchange();
            final ExchangeSpecification btceExchangeSpecification = btceExchange.getDefaultExchangeSpecification();
            btceExchangeSpecification.setApiKey(settings.getBtceKey());
            btceExchangeSpecification.setSecretKey(settings.getBtceSecret());
            btceExchangeSpecification.setExchangeName("btce");

    return new ArrayList<Exchange>() {
        {
            add(ExchangeFactory.INSTANCE.createExchange(btceExchangeSpecification));
        }
    };
    }
}
