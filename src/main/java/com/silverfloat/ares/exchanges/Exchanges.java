package com.silverfloat.ares.exchanges;

import com.silverfloat.ares.configuration.Settings;
import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeFactory;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.btce.v3.BTCEExchange;
import com.xeiam.xchange.cryptsy.CryptsyExchange;

import java.util.ArrayList;
import java.util.List;


public class Exchanges {

    private Settings settings;

    public Exchanges(Settings settings) {
        this.settings = settings;
    }

    public Exchange btce() {
        final BTCEExchange btceExchange = new BTCEExchange();
        final ExchangeSpecification btceExchangeSpecification = btceExchange.getDefaultExchangeSpecification();
        btceExchangeSpecification.setApiKey(settings.getBtceKey());
        btceExchangeSpecification.setSecretKey(settings.getBtceSecret());
        btceExchangeSpecification.setExchangeName("btce");
        return ExchangeFactory.INSTANCE.createExchange(btceExchangeSpecification);
    }

    public Exchange cryptsy() {
        final CryptsyExchange cryptsyExchange = new CryptsyExchange();
        final ExchangeSpecification cryptsyExchangeSpecification = cryptsyExchange.getDefaultExchangeSpecification();
        cryptsyExchangeSpecification.setApiKey(settings.getCryptsyKey());
        cryptsyExchangeSpecification.setSecretKey(settings.getCryptsySecret());
        cryptsyExchangeSpecification.setExchangeName("cryptsy");
        return ExchangeFactory.INSTANCE.createExchange(cryptsyExchangeSpecification);
    }

    public List<Exchange> asAList() {
        return new ArrayList<Exchange>() {
            {
                add(btce());
                add(cryptsy());
            }
        };
    }
}
