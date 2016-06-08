package com.example.apps.asystentremontu.shopping_list;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.Database;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.sql.language.Delete;
import com.raizlabs.android.dbflow.sql.language.Select;
import com.raizlabs.android.dbflow.structure.BaseModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Beryl
 * on 12.05.2016
 */
public class GeneralShoppingDatabase {

    @Database(name = ShoppingDatabaseInit.SHOPPING_DATABASE_NAME, version = ShoppingDatabaseInit.VERSION)
    public class ShoppingDatabaseInit {
        public static final String SHOPPING_DATABASE_NAME = "shoppingDatabaseName";
        public static final int VERSION = 2;

    }

    public static class ShoppingListConfig {
        public String product;
        public boolean isChecked;
        public String date;

        public ShoppingListConfig(String product, boolean isChecked, String date) {
            this.product = product;
            this.isChecked = isChecked;
            this.date = date;
        }


    }

    @Table(database = ShoppingDatabaseInit.class)
    public static class ShoppingDatabase extends BaseModel {
        @Column
        @PrimaryKey(autoincrement = true)
        long id;

        @Column
        public String product;

        @Column
        public boolean isChecked;

        @Column
        public String date;

    }

    @Table(database = ShoppingDatabaseInit.class)
    public static class HistoryShoppingDatabase extends BaseModel {
        @Column
        @PrimaryKey(autoincrement = true)
        int id;

        @Column
        public String product;

        @Column
        public Boolean isChecked;

        @Column
        public String date;


    }

    public static List<ShoppingDatabase> createActualShoppingListConfig(List<ShoppingListConfig> config) {
        List<ShoppingDatabase> listConfig = new ArrayList<ShoppingDatabase>();
        for (ShoppingListConfig shoppingListConfig : config) {
            ShoppingDatabase newConfig = new ShoppingDatabase();
            newConfig.product = shoppingListConfig.product;
            newConfig.isChecked = shoppingListConfig.isChecked;
            newConfig.date = shoppingListConfig.date;
            listConfig.add(newConfig);

        }
        return listConfig;

    }

    public static List<HistoryShoppingDatabase> createHistoryShoppingListConfig(List<ShoppingListConfig> historyConfig) {
        List<HistoryShoppingDatabase> listConfig = new ArrayList<HistoryShoppingDatabase>();
        for (ShoppingListConfig shoppingListConfig : historyConfig) {
            HistoryShoppingDatabase newConfig = new HistoryShoppingDatabase();
            newConfig.product = shoppingListConfig.product;
            newConfig.isChecked = shoppingListConfig.isChecked;
            newConfig.date = shoppingListConfig.date;
            listConfig.add(newConfig);

        }
        return listConfig;
    }

    public static void saveActualShoppingListConfig(List<ShoppingDatabase> config) {
        for (ShoppingDatabase shoppingDatabase : config) {
            shoppingDatabase.save();
        }
    }

    public static void saveHistoryShoppingListConfig(List<HistoryShoppingDatabase> historyConfig) {
        for (HistoryShoppingDatabase historyShoppingDatabase : historyConfig) {
            historyShoppingDatabase.save();
        }
    }


    public static List<ShoppingDatabase> getShoppingListConfig() {
        List<ShoppingDatabase> config = new Select().from(ShoppingDatabase.class).queryList();
        return config;
    }

    public static List<HistoryShoppingDatabase> getHistoryShoppingListConfig() {
        List<HistoryShoppingDatabase> historyConfig = new Select().from(HistoryShoppingDatabase.class).queryList();
        return historyConfig;
    }


    public static void clearShoppingDatabase() {
        Delete.table(ShoppingDatabase.class);
    }

    public static void clearHistoryShoppingDatabase() {
        Delete.table(HistoryShoppingDatabase.class);
    }


}


