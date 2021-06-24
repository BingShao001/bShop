package com.ice.bshop.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Properties;

public class PropertiesUtils {
    private static final Logger logger = LoggerFactory.getLogger(PropertiesUtils.class);
    static Properties setting = null;
    static {
        setting  = new Properties();
        try {
            setting.load(PropertiesUtils.class.getClassLoader().getResourceAsStream("application.properties"));
        } catch (IOException e) {
            logger.info("AsynTaskExecutors IOException={}",e);
        }
    }

    /**
     *
     * @param key
     * @return
     */
    public static String getValue(String key){
        return setting.getProperty(key);
    }
}
