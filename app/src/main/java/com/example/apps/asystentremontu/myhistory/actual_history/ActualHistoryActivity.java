package com.example.apps.asystentremontu.myhistory.actual_history;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.example.apps.asystentremontu.R;
import com.example.apps.asystentremontu.database.AssistantUserConfig;
import com.example.apps.asystentremontu.myhistory.newelement.CommunicationWithDatabase;
import com.example.apps.asystentremontu.myhistory.newelement.ConfigListSingleton;

import java.io.File;
import java.util.List;

/**
 * Created by Beryl
 * on 11.05.2016
 */
public class ActualHistoryActivity extends CommunicationWithDatabase {
    RecyclerView recyclerView;
    List<AssistantUserConfig> config;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actual_history);
        config = ConfigListSingleton.getConfigInstance().getConfig();


        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        final AdapterForRecyclerView adapterForRecyclerView = new AdapterForRecyclerView(this);
        adapterForRecyclerView.setConfig(config, new RefleshRecyclerViewListener() {
            @Override
            public void onReflesh(int position) {
                File file = new File(ConfigListSingleton.getConfigInstance().getConfig().get(position).pathToImg);
                if (file.exists()) {
                    file.delete();
                }
                recyclerView.removeViewAt(position);
                config.remove(position);
                createItemToDb(config);
                Toast.makeText(getApplicationContext(), "usunięto " + String.valueOf(position + 1) + " element", Toast.LENGTH_SHORT).show();
                adapterForRecyclerView.notifyItemRemoved(position);
                adapterForRecyclerView.notifyItemRangeChanged(position, config.size());
                adapterForRecyclerView.notifyDataSetChanged();
            }
        });
        recyclerView.setAdapter(adapterForRecyclerView);
        Toast.makeText(getApplicationContext(), "kliknij na pozycję aby modyfikować", Toast.LENGTH_SHORT).show();
    }


    public static interface RefleshRecyclerViewListener {
        public void onReflesh(int position);
    }
}
