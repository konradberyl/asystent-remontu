package com.example.krystiano.asystentremontu;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.krystiano.asystentremontu.myhistory.MyHistoryMainClass;
import com.raizlabs.android.dbflow.config.FlowManager;


public class MainActivity extends Activity {
    Button myHistoryButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myHistoryButton=(Button)findViewById(R.id.my_history_button);

        myHistoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent openMyHistory = new Intent(getApplicationContext(), MyHistoryMainClass.class);
                startActivity(openMyHistory);
            }
        });


    }

    public void closeProgram(View view){
        System.exit(0);
    }

    public void openKalkulator(View view) {
        Intent intent=new Intent(this,kalkulator.class);
        startActivity(intent);

    }
}
