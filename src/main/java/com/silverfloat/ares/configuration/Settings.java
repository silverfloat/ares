package com.silverfloat.ares.configuration;

import java.math.BigDecimal;
import java.util.Properties;

import static java.lang.System.getProperties;

public class Settings {

    private static final String BTCE_KEY = "btcekey";
    private static final String BTCE_SECRET = "btcesecret";
    private static final String CRYPTSY_KEY = "cryptsykey";
    private static final String CRYPTSY_SECRET = "cryptsysecret";
    private static final String MINIMUM_TRADABLE_AMOUNT = "minimum.tradable.amount";
    private static final String QUERY_EXCHANGES_DELAY = "query.exchanges.delay";
    public static final String PENDING_ORDER_EXPIRATION_TIME_IN_SECONDS = "pending.order.expiration.time.in.seconds";

    private final Properties properties;

    public Settings(Properties properties) {
        this.properties = properties;
    }

    public String getBtceKey() {
        return properties.getProperty(BTCE_KEY);
    }

    public String getBtceSecret() {
        return properties.getProperty(BTCE_SECRET);
    }

    public String getCryptsyKey() {
        return properties.getProperty(CRYPTSY_KEY);
    }

    public String getCryptsySecret() {
        return properties.getProperty(CRYPTSY_SECRET);
    }

    public BigDecimal getMinimumTradableAmount() {
        return new BigDecimal(properties.getProperty(MINIMUM_TRADABLE_AMOUNT));
    }

    public int getQueryExchangesDelay() {
        return Integer.parseInt(properties.getProperty(QUERY_EXCHANGES_DELAY));
    }

    public int getPendingOrderExpirationTimeInSeconds() {
        return Integer.parseInt(properties.getProperty(PENDING_ORDER_EXPIRATION_TIME_IN_SECONDS));
    }

    public String getRockefellerBaseUrl() {
        final Properties environmentProperties = getProperties();
        return environmentProperties.getProperty("SILVERFLOAT_ROCKEFELLER_FIND_NETWORK_HOST") +
                ":" + environmentProperties.getProperty("SILVERFLOAT_ROCKEFELLER_FIND_NETWORK_PORT") +
                "/rockefeller";
    }

    public String getCaronteBaseUrl() {
        final Properties environmentProperties = getProperties();
        return environmentProperties.getProperty("SILVERFLOAT_CARONTE_FIND_NETWORK_HOST") +
                ":" + environmentProperties.getProperty("SILVERFLOAT_CARONTE_FIND_NETWORK_PORT") +
                "/caronte";
    }

}