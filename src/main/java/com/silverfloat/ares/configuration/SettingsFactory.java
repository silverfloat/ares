package com.silverfloat.ares.configuration;

import org.glassfish.hk2.api.Factory;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by ocean on 12/10/14.
 */
public class SettingsFactory implements Factory<Settings> {
    @Override
    public Settings provide() {
        try (InputStream propertiesStream = new FileInputStream("ares.properties")) {
            final Properties properties = new Properties();
            properties.load(propertiesStream);
            return new Settings(properties);
        } catch (IOException e) {
            throw new RuntimeException("An error occurred while loading the properties file.", e);
        }
    }

    @Override
    public void dispose(Settings instance) {

    }
}
