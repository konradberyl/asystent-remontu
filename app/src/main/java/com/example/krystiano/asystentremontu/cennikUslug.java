package com.example.krystiano.asystentremontu;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;

public class cennikUslug extends Activity {
    private ListView listView1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_cennik_uslug);

        rowBean rowBean_data[] = new rowBean[]{

                new rowBean(R.drawable.panele, "Panele","40"),
                new rowBean(R.drawable.plytki, "Płytki","50"),
                new rowBean(R.drawable.malowanie,"Malowanie","30"),
                new rowBean(R.drawable.kostka,"Kostka","20")


        };

        rowAdapter adapter = new rowAdapter(this, R.layout.content_cennik_uslug, rowBean_data);

        listView1 = (ListView) findViewById(R.id.listView);

        listView1.setAdapter(adapter);
    }
}




