package com.example.krystiano.asystentremontu;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class kalkulator extends cennikUslug {
    private ListView listViewWithItems;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_kalkulator);

      //  setContentView(R.layout.listview_items);


    kalkulatorRowBean kalkulatorRowBean_data[] = new kalkulatorRowBean[]{

            new kalkulatorRowBean("Panele"),
            new kalkulatorRowBean("Płytki"),
            new kalkulatorRowBean("Malowanie"),
            new kalkulatorRowBean("Kostka"),
            new kalkulatorRowBean("Tapetowanie"),
            new kalkulatorRowBean("Gładź")




    };

    kalkulatorAdapter adapter = new kalkulatorAdapter(this, R.layout.listview_items, kalkulatorRowBean_data);

    listViewWithItems = (ListView) findViewById(R.id.listview_calculator);
        listViewWithItems.setItemsCanFocus(true);
    listViewWithItems.setAdapter(adapter);


}
   public void licz(View view){
       EditText numA  = (EditText)findViewById(R.id.type_quantity_edittext);
       EditText wynik = (EditText)findViewById(R.id.price);
       float num1 = Float.parseFloat(numA.getText().toString());
       float eq = 0;
       switch(view.getId()){
           case R.id.add_button:
               eq = num1*50;
               Toast.makeText(getApplicationContext(), "this is my Toast message!!! =)",
                       Toast.LENGTH_LONG).show();
               break;

       }

       wynik.setText(String.format("%f", eq));
   }


   }

  // float num2 = Float.parseFloat(numB.getText().toString());





