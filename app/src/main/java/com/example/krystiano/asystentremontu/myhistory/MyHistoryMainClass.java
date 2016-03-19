package com.example.krystiano.asystentremontu.myhistory;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.krystiano.asystentremontu.database.AssistantUserConfig;
import com.example.krystiano.asystentremontu.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Beryl
 * on 19.03.2016
 * All rights reserved
 */
public class MyHistoryMainClass extends CommunicationWithDatabase {
    private TextView actualHistoryTextview, newElementTextview,textToShow;
    private EditText textToWriteSmthng;
    private AssistantUserConfig actualConfig;
    private ArrayList<AssistantUserConfig> configList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_history);
        actualHistoryTextview = (TextView) findViewById(R.id.actual_history_textview);
        newElementTextview = (TextView) findViewById(R.id.new_element_textview);
        textToWriteSmthng = (EditText) findViewById(R.id.text_to_writesomething);
        textToShow=(TextView)findViewById(R.id.text_to_show);
        getItemFromDb();
        if(!configList.isEmpty()){
            textToShow.setText(configList.get(configList.size()-1).comment);
        }

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

    @Override
    protected List<AssistantUserConfig> getAssistantUserConfig() {
        return configList;
    }

}
