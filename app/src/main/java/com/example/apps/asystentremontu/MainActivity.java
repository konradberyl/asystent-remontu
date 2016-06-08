package com.example.apps.asystentremontu;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.apps.asystentremontu.calculator.CalculatorActivity;
import com.example.apps.asystentremontu.myhistory.MyHistoryMainClass;
import com.example.apps.asystentremontu.shopping_list.ShoppingListMainActivity;


public class MainActivity extends Activity {
    Button myHistoryButton, shoppingListButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myHistoryButton = (Button) findViewById(R.id.my_history_button);
        shoppingListButton = (Button) findViewById(R.id.shopping_list_button);

        myHistoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent openMyHistory = new Intent(getApplicationContext(), MyHistoryMainClass.class);
                startActivity(openMyHistory);
            }
        });
        shoppingListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent openShoppingList = new Intent(getApplicationContext(), ShoppingListMainActivity.class);
                startActivity(openShoppingList);
            }
        });


    }

    public void closeProgram(View view) {
        System.exit(0);
    }

    public void openCalculatorActivity(View view) {
        Intent intent = new Intent(this, CalculatorActivity.class);
        startActivity(intent);

    }


}
