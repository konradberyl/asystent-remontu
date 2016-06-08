package com.example.apps.asystentremontu.shopping_list;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Beryl
 * on 12.05.2016
 */
public class ActualShoppingListSingleton {
    private static ActualShoppingListSingleton ourInstance;

    private List<GeneralShoppingDatabase.ShoppingListConfig> config;
    private List<GeneralShoppingDatabase.ShoppingListConfig> historyConfig;

    private ActualShoppingListSingleton() {
        config = new ArrayList<GeneralShoppingDatabase.ShoppingListConfig>();
        historyConfig = new ArrayList<GeneralShoppingDatabase.ShoppingListConfig>();
    }

    public static ActualShoppingListSingleton getInstance() {
        if (ourInstance == null) {
            ourInstance = new ActualShoppingListSingleton();
        }
        return ourInstance;
    }

    public List<GeneralShoppingDatabase.ShoppingListConfig> getConfig() {
        return config;
    }

    public void setConfig(List<GeneralShoppingDatabase.ShoppingListConfig> config) {
        this.config = config;
    }

    public List<GeneralShoppingDatabase.ShoppingListConfig> getHistoryConfig() {
        return historyConfig;
    }

    public void setHistoryConfig(List<GeneralShoppingDatabase.ShoppingListConfig> historyConfig) {
        this.historyConfig = historyConfig;
    }


}
