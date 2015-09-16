package com.silverfloat.ares.configuration;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public interface PropertiesLoader {

    public static Properties loadProperties() {
        try (final InputStream input = new FileInputStream("ares.properties")) {
            Properties properties = new Properties();
            properties.load(input);
            return properties;
        } catch (IOException e) {
            throw new RuntimeException("Cant find ares.properties!");
        }
    }

}
