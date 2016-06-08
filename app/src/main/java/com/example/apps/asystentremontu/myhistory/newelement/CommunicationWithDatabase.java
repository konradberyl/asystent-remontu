package com.example.apps.asystentremontu.myhistory.newelement;

import android.app.Activity;

import com.example.apps.asystentremontu.database.AssistantUserConfig;
import com.example.apps.asystentremontu.database.DbManager;
import com.example.apps.asystentremontu.database.MyHistoryDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Beryl
 * on 19.03.2016
 */
public abstract class CommunicationWithDatabase extends Activity {

    private List<AssistantUserConfig> config = new ArrayList<AssistantUserConfig>();
    private List<MyHistoryDatabase> configFromDb;

    protected void createItemToDb(List<AssistantUserConfig> cfgs) {
        DbManager.clearAssistantDatabase();
        DbManager.saveAssistantConfig(DbManager.createAssistantConfig(cfgs));
        ConfigListSingleton.getConfigInstance().setConfig(cfgs);
    }

    protected void getItemFromDb() {
        configFromDb = DbManager.getAssistantConfig();
        if (!configFromDb.isEmpty()) {
            for (MyHistoryDatabase myHistoryDatabase : configFromDb) {
                AssistantUserConfig assistantUserConfig = new AssistantUserConfig(myHistoryDatabase.comment, myHistoryDatabase.pathToImg, myHistoryDatabase.date, myHistoryDatabase.price);
                config.add(assistantUserConfig);
            }
            ConfigListSingleton.getConfigInstance().setConfig(config);

        }
    }
}

