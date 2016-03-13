package com.example.krystiano.asystentremontu;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void closeProgram(View view){
        System.exit(0);
    }

    public void openCennikUslug(View view) {
        Intent intent=new Intent(this,cennikUslug.class);
        startActivity(intent);

    }
    public void openKalkulator(View view) {
        Intent intent=new Intent(this,kalkulator.class);
        startActivity(intent);

    }
}
