package com.example.krystiano.asystentremontu.myhistory;

import android.app.Activity;

import com.example.krystiano.asystentremontu.database.AssistantUserConfig;
import com.example.krystiano.asystentremontu.database.DbManager;
import com.example.krystiano.asystentremontu.database.MyHistoryDatabase;

import java.util.List;

/**
 * Created by Beryl
 * on 19.03.2016
 */
public abstract  class CommunicationWithDatabase  extends Activity{

    protected  abstract List<AssistantUserConfig> getAssistantUserConfig();

    protected List<MyHistoryDatabase> configFromDb;

    protected void createItemToDb(List<AssistantUserConfig> cfgs) {
        DbManager.clearAssistantDatabase();
        DbManager.saveAssistantConfig(DbManager.createAssistantConfig(cfgs));
    }

    protected void getItemFromDb() {
        configFromDb = DbManager.getAssistantConfig();
        if (!configFromDb.isEmpty()) {
            for (MyHistoryDatabase myHistoryDatabase : configFromDb) {
                AssistantUserConfig assistantUserConfig = new AssistantUserConfig(myHistoryDatabase.comment, myHistoryDatabase.pathToImg, myHistoryDatabase.date, myHistoryDatabase.price);
                getAssistantUserConfig().add(assistantUserConfig);
            }

        }
    }
}

