package com.example.krystiano.asystentremontu.myhistory.newelement;

import com.example.krystiano.asystentremontu.database.AssistantUserConfig;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Beryl
 * on 11.05.2016
 */
public class ConfigListSingleton {
    private static ConfigListSingleton configInstance;
    private List<AssistantUserConfig> config;

    private ConfigListSingleton() {
        config = new ArrayList<AssistantUserConfig>();

    }


    public static ConfigListSingleton getConfigInstance() {
        if (configInstance == null) {
            configInstance = new ConfigListSingleton();
        }
        return configInstance;
    }

    public List<AssistantUserConfig> getConfig() {
        return this.config;
    }

    public void setConfig(List<AssistantUserConfig> config) {
        this.config = config;
    }

}
