package com.example.krystiano.asystentremontu.database;

import com.raizlabs.android.dbflow.sql.language.Delete;
import com.raizlabs.android.dbflow.sql.language.Select;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Beryl
 * on 19.03.2016
 */
public class DbManager {

    public static List<MyHistoryDatabase> createAssistantConfig(List<AssistantUserConfig> config){
      List<MyHistoryDatabase> userConfig = new ArrayList<MyHistoryDatabase>();
        for(AssistantUserConfig assistantUserConfig : config){
            MyHistoryDatabase newConfig = new MyHistoryDatabase();
            newConfig.comment = assistantUserConfig.comment;
            newConfig.date = assistantUserConfig.date;
            newConfig.pathToImg = assistantUserConfig.pathToImg;
            newConfig.price = assistantUserConfig.price;
            userConfig.add(newConfig);
        }
        return userConfig;

    }

    public static void saveAssistantConfig(List<MyHistoryDatabase> configs){
        for(MyHistoryDatabase myHistoryDatabase : configs)
            myHistoryDatabase.save();
    }

    public static List<MyHistoryDatabase> getAssistantConfig(){
        List<MyHistoryDatabase> config = new Select().from(MyHistoryDatabase.class).queryList();
        return config;
    }

    public static void clearAssistantDatabase(){
        Delete.table(MyHistoryDatabase.class);
    }
}
