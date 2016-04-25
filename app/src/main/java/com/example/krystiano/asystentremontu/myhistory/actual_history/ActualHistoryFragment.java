package com.example.krystiano.asystentremontu.myhistory.actual_history;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.krystiano.asystentremontu.R;
import com.example.krystiano.asystentremontu.database.AssistantUserConfig;
import com.example.krystiano.asystentremontu.myhistory.MyHistoryMainClass;

import java.util.List;

/**
 * Created by Beryl
 * on 21.03.2016
 */
public class ActualHistoryFragment extends Fragment {
    RecyclerView recyclerView;
    List<AssistantUserConfig> config;
    MyHistoryMainClass.DeleteListener deleteListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.actual_history, container, false);
    }

    public void setFragmentConfigAndListener(List<AssistantUserConfig> config, MyHistoryMainClass.DeleteListener deleteListener) {
        this.config = config;
        this.deleteListener = deleteListener;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        final AdapterForRecyclerView adapterForRecyclerView = new AdapterForRecyclerView(getContext());
        adapterForRecyclerView.setConfig(config, deleteListener, new RefleshRecyclerViewListener() {
            @Override
            public void onReflesh(int position) {
                Toast.makeText(getContext(), "refleshing recyclerview", Toast.LENGTH_SHORT).show();
                recyclerView.removeViewAt(position);
                adapterForRecyclerView.notifyItemRemoved(position);
                adapterForRecyclerView.notifyItemRangeChanged(position, config.size());
                adapterForRecyclerView.notifyDataSetChanged();
            }
        });
        recyclerView.setAdapter(adapterForRecyclerView);


    }

    public static interface RefleshRecyclerViewListener{
        public void onReflesh(int position);
    }
}
