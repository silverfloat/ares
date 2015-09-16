package com.silverfloat.ares.orders;

import com.silverfloat.ares.configuration.Settings;
import com.silverfloat.ares.orders.filtering.MinimumQuantityOrderFilter;
import com.xeiam.xchange.dto.trade.LimitOrder;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.xeiam.xchange.currency.CurrencyPair.LTC_BTC;
import static com.xeiam.xchange.dto.Order.OrderType.ASK;
import static java.util.stream.Collectors.toList;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MinimumQuantityOrderFilterTest {

    private final Settings settings = mock(Settings.class);
    private final BigDecimal MINIMUM_TRADABLE_AMOUNT = new BigDecimal(0.3D);

    @Before
    public void setUp() throws Exception {
        when(settings.getMinimumTradableAmount()).thenReturn(MINIMUM_TRADABLE_AMOUNT);
    }

    @Test
    public void shouldReturnJustOpenOrdersWhichQuantityIsAboveMinimumTradableAmount() throws Exception {
        MinimumQuantityOrderFilter ordersFilter = new MinimumQuantityOrderFilter(settings);
        final List<LimitOrder> filteredOrders = openOrders().stream().filter(ordersFilter).collect(toList());
        assertThat(filteredOrders.size(), is(3));
    }

    private List<LimitOrder> openOrders() {
        LimitOrder validOrder = new LimitOrder(ASK,new BigDecimal(0.8D), LTC_BTC,"someId",new Date(),new BigDecimal(0.9));
        LimitOrder inValidOrder = new LimitOrder(ASK,new BigDecimal(0.1D), LTC_BTC,"someId",new Date(),new BigDecimal(0.9));
        List<LimitOrder> orders = new ArrayList<>();
        orders.add(validOrder);
        orders.add(validOrder);
        orders.add(validOrder);
        orders.add(inValidOrder);
        return orders;
    }
}
