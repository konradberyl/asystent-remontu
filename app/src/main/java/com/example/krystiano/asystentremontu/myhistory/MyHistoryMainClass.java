package com.example.krystiano.asystentremontu.myhistory;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.krystiano.asystentremontu.AssistantUserConfig;
import com.example.krystiano.asystentremontu.R;
import com.example.krystiano.asystentremontu.database.DbManager;
import com.example.krystiano.asystentremontu.database.MyHistoryDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Beryl
 * on 19.03.2016
 * All rights reserved
 */
public class MyHistoryMainClass extends Activity {
    private TextView actualHistoryTextview, newElementTextview,textToShow;
    private EditText textToWriteSmthng;
    private AssistantUserConfig actualConfig;
    private ArrayList<AssistantUserConfig> configList = new ArrayList<AssistantUserConfig>();
    private List<MyHistoryDatabase> configListFromDb ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_history);
        actualHistoryTextview = (TextView) findViewById(R.id.actual_history_textview);
        newElementTextview = (TextView) findViewById(R.id.new_element_textview);
        textToWriteSmthng = (EditText) findViewById(R.id.text_to_writesomething);
        textToShow=(TextView)findViewById(R.id.text_to_show);
        getItemFromDb();

        actualHistoryTextview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        newElementTextview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = textToWriteSmthng.getText().toString();
                textToShow.setText(text);
                actualConfig = new AssistantUserConfig(text, "sciezka", "data aktualna", "cenahehe");
                configList.add(actualConfig);
                createItemToDb(configList);
            }
        });


    }

    private void createItemToDb(List<AssistantUserConfig> cfgs) {
        DbManager.clearAssistantDatabase();
        DbManager.saveAssistantConfig(DbManager.createAssistantConfig(cfgs));
    }

    private void getItemFromDb() {
        configListFromDb = DbManager.getAssistantConfig();
        if (!configListFromDb.isEmpty()) {
            for (MyHistoryDatabase myHistoryDatabase : configListFromDb) {
                AssistantUserConfig assistantUserConfig = new AssistantUserConfig(myHistoryDatabase.comment, myHistoryDatabase.pathToImg, myHistoryDatabase.date, myHistoryDatabase.price);
                configList.add(assistantUserConfig);
            }
            textToShow.setText(configList.get(0).comment);

        }
    }
}
