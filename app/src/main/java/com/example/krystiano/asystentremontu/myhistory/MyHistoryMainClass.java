package com.example.krystiano.asystentremontu.myhistory;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;


import com.example.krystiano.asystentremontu.R;
import com.example.krystiano.asystentremontu.database.AssistantUserConfig;
import com.example.krystiano.asystentremontu.myhistory.actual_history.ActualHistoryFragment;
import com.example.krystiano.asystentremontu.myhistory.newelement.CommunicationWithDatabase;
import com.example.krystiano.asystentremontu.myhistory.newelement.NewElementFragment;
import com.example.krystiano.asystentremontu.myhistory.newelement.OnNewElementAdded;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Beryl
 * on 19.03.2016
 * All rights reserved
 */
public class MyHistoryMainClass extends CommunicationWithDatabase {
    private Button actualHistoryButton, newElementButton;
    private AssistantUserConfig actualConfig;
    private ArrayList<AssistantUserConfig> configList = new ArrayList<>();
    private List<View> views = new ArrayList<>();
    private FrameLayout fragmentContainer;
    ActualHistoryFragment.RefleshRecyclerViewListener refleshRecyclerViewListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_history);
        views.clear();
        actualHistoryButton = (Button) findViewById(R.id.actual_history_button);
        newElementButton = (Button) findViewById(R.id.new_element_button);
        fragmentContainer = (FrameLayout) findViewById(R.id.fragment_container);
        views.add(actualHistoryButton);
        views.add(newElementButton);
        views.add(fragmentContainer);
        getItemFromDb();



        actualHistoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActualHistoryFragment actualHistoryFragment = new ActualHistoryFragment();
                actualHistoryFragment.setFragmentConfigAndListener(configList, new DeleteListener() {
                    @Override
                    public void onDelete(int position) {
                        configList.remove(position);
                        createItemToDb(configList);


                    }
                });
                if (!configList.isEmpty()) {

                    placeFragmentInContainer(actualHistoryFragment);
                    hideAllViewsWithout(fragmentContainer);
                } else {
                    Toast.makeText(getApplicationContext(), "dodaj najpierw nowy element!", Toast.LENGTH_SHORT).show();
                }

            }
        });
        newElementButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NewElementFragment newElementFragment = new NewElementFragment();
                newElementFragment.setCallbacks(new OnNewElementAdded() {
                    @Override
                    public void onAdded(AssistantUserConfig newCfg) {
                        configList.add(newCfg);
                        createItemToDb(configList);
                        onBackPressed();
                        Toast.makeText(getApplicationContext(), "dodano nowy element", Toast.LENGTH_SHORT).show();

                    }

                });
                placeFragmentInContainer(newElementFragment);
                hideAllViewsWithout(fragmentContainer);

            }
        });


    }

    public void setRefleshListener(ActualHistoryFragment.RefleshRecyclerViewListener refleshRecyclerViewListener){
        this.refleshRecyclerViewListener = refleshRecyclerViewListener;
    }
    public void placeFragmentInContainer(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment).commit();
    }

    @Override
    protected List<AssistantUserConfig> getAssistantUserConfig() {
        return configList;
    }

    @Override
    protected List<View> getViews() {
        return views;
    }

    public static interface DeleteListener{
        public void onDelete(int position);
    }

    @Override
    public void onBackPressed() {
        if (fragmentContainer.getVisibility()== View.VISIBLE){
            unhideAllViewsWithout(fragmentContainer);
        }
        else{
            super.onBackPressed();
        }
    }
}
