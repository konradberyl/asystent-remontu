package com.example.krystiano.asystentremontu.myhistory;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


import com.example.krystiano.asystentremontu.R;
import com.example.krystiano.asystentremontu.myhistory.actual_history.ActualHistoryActivity;
import com.example.krystiano.asystentremontu.myhistory.newelement.CommunicationWithDatabase;
import com.example.krystiano.asystentremontu.myhistory.newelement.ConfigListSingleton;
import com.example.krystiano.asystentremontu.myhistory.newelement.NewElementActivity;


/**
 * Created by Beryl
 * on 19.03.2016
 * All rights reserved
 */
public class MyHistoryMainClass extends CommunicationWithDatabase {
    private Button actualHistoryButton, newElementButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_history);
        actualHistoryButton = (Button) findViewById(R.id.actual_history_button);
        newElementButton = (Button) findViewById(R.id.new_element_button);
        getItemFromDb();


        actualHistoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!ConfigListSingleton.getConfigInstance().getConfig().isEmpty()) {
                    Intent startActualHistory = new Intent(getApplicationContext(), ActualHistoryActivity.class);
                    startActivity(startActualHistory);
                } else {
                    Toast.makeText(getApplicationContext(), "najpierw dodaj element!", Toast.LENGTH_SHORT).show();
                }


            }
        });
        newElementButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startNewElement = new Intent(getApplicationContext(), NewElementActivity.class);
                startActivity(startNewElement);
            }
        });
    }

}
