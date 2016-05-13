package com.example.krystiano.asystentremontu.shopping_list;

import android.support.v4.app.Fragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Beryl
 * on 12.05.2016
 */
public  class DbConfigHelper extends Fragment {
    private List<GeneralShoppingDatabase.ShoppingListConfig> config = new ArrayList<>();
    private List<GeneralShoppingDatabase.ShoppingListConfig> historyConfig = new ArrayList<>();

    private List<GeneralShoppingDatabase.ShoppingDatabase> configFromDb = new ArrayList<>();
    private List<GeneralShoppingDatabase.HistoryShoppingDatabase> historyConfigFromDb = new ArrayList<>();

    protected void createItemToDb(List<GeneralShoppingDatabase.ShoppingListConfig> cfgs){
        GeneralShoppingDatabase.clearShoppingDatabase();
        GeneralShoppingDatabase.saveActualShoppingListConfig(GeneralShoppingDatabase.createActualShoppingListConfig(cfgs));

    }
    protected void createHistoryItemToDb(List<GeneralShoppingDatabase.ShoppingListConfig> cfgs){
        GeneralShoppingDatabase.clearHistoryShoppingDatabase();
        GeneralShoppingDatabase.saveHistoryShoppingListConfig(GeneralShoppingDatabase.createHistoryShoppingListConfig(cfgs));
      //  ActualShoppingListSingleton.getInstance().setConfig(cfgs);
    }
    protected  void getItemFromDb(){
        configFromDb =GeneralShoppingDatabase.getShoppingListConfig();
        historyConfigFromDb = GeneralShoppingDatabase.getHistoryShoppingListConfig();
        if(!configFromDb.isEmpty()){
            for(GeneralShoppingDatabase.ShoppingDatabase shoppingDatabase : configFromDb){
                GeneralShoppingDatabase.ShoppingListConfig shoppingListConfig = new GeneralShoppingDatabase.ShoppingListConfig(shoppingDatabase.product,shoppingDatabase.isChecked,shoppingDatabase.date);
                config.add(shoppingListConfig);
            }
            ActualShoppingListSingleton.getInstance().setConfig(config);
        }

        if(!historyConfigFromDb.isEmpty()){
            for(GeneralShoppingDatabase.HistoryShoppingDatabase historyShoppingDatabase : historyConfigFromDb){
                GeneralShoppingDatabase.ShoppingListConfig shoppingListConfig = new GeneralShoppingDatabase.ShoppingListConfig(historyShoppingDatabase.product,historyShoppingDatabase.isChecked,historyShoppingDatabase.date);
                historyConfig.add(shoppingListConfig);
            }
        }
        ActualShoppingListSingleton.getInstance().setHistoryConfig(historyConfig);
    }
}
