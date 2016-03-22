package com.example.krystiano.asystentremontu.myhistory.newelement;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.Toast;

import com.example.krystiano.asystentremontu.database.AssistantUserConfig;
import com.example.krystiano.asystentremontu.database.DbManager;
import com.example.krystiano.asystentremontu.database.MyHistoryDatabase;

import java.util.List;

/**
 * Created by Beryl
 * on 19.03.2016
 */
public abstract class CommunicationWithDatabase extends FragmentActivity {

    protected abstract List<AssistantUserConfig> getAssistantUserConfig();

    protected abstract List<View> getViews();

    private List<MyHistoryDatabase> configFromDb;

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

    protected void hideAllViewsWithout(View view) {
        for (int i = 0; i < getViews().size(); i++) {
            if (getViews().get(i).equals(view)) {
                getViews().get(i).setVisibility(View.VISIBLE);
            } else {
                getViews().get(i).setVisibility(View.GONE);
            }
        }

    }
    protected void unhideAllViewsWithout(View view) {
        for (int i = 0; i < getViews().size(); i++) {
            if (getViews().get(i).equals(view)) {
                getViews().get(i).setVisibility(View.GONE);
            } else {
                getViews().get(i).setVisibility(View.VISIBLE);
            }
        }

    }
}

