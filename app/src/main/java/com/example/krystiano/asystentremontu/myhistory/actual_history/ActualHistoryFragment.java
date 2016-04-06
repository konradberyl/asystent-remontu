package com.example.krystiano.asystentremontu.myhistory.actual_history;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.krystiano.asystentremontu.R;
import com.example.krystiano.asystentremontu.database.AssistantUserConfig;

import java.util.List;

/**
 * Created by Beryl
 * on 21.03.2016
 */
public class ActualHistoryFragment extends Fragment {
    RecyclerView recyclerView;
    List<AssistantUserConfig> config;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.actual_history,container,false);
    }
    public  void setFragmentConfig(List<AssistantUserConfig>config){
        this.config = config;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = (RecyclerView)view.findViewById(R.id.recycler_view);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        AdapterForRecyclerView adapterForRecyclerView = new AdapterForRecyclerView(getContext());
        adapterForRecyclerView.setConfig(config);
        recyclerView.setAdapter(adapterForRecyclerView);

    }
}
