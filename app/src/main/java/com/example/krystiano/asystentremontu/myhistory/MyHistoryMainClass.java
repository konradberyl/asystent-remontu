package com.example.krystiano.asystentremontu.myhistory;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.krystiano.asystentremontu.database.AssistantUserConfig;
import com.example.krystiano.asystentremontu.R;
import com.example.krystiano.asystentremontu.myhistory.newelement.CommunicationWithDatabase;
import com.example.krystiano.asystentremontu.myhistory.newelement.NewElementFragment;
import com.example.krystiano.asystentremontu.myhistory.newelement.OnNewElementAdded;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Beryl
 * on 19.03.2016
 */
public class MyHistoryMainClass extends CommunicationWithDatabase {
    private TextView actualHistoryTextview, newElementTextview;
    private AssistantUserConfig actualConfig;
    private ArrayList<AssistantUserConfig> configList = new ArrayList<>();
    private List<View> views = new ArrayList<>();
    private FrameLayout fragmentContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_history);
        views.clear();
        actualHistoryTextview = (TextView) findViewById(R.id.actual_history_textview);
        newElementTextview = (TextView) findViewById(R.id.new_element_textview);
        fragmentContainer = (FrameLayout) findViewById(R.id.fragment_container);
        fragmentContainer.setVisibility(View.GONE);
        views.add(actualHistoryTextview);
        views.add(newElementTextview);
        views.add(fragmentContainer);
        //getItemFromDb();

        actualHistoryTextview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        newElementTextview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideAllViewsWithout(fragmentContainer);
                NewElementFragment newElementFragment = new NewElementFragment();
                newElementFragment.setCallbacks(new OnNewElementAdded() {
                    @Override
                    public void onAdded(AssistantUserConfig newCfg) {
                        configList.add(newCfg);
                        onBackPressed();
                        Toast.makeText(getApplicationContext(), "dodano cene: " + configList.get(configList.indexOf(newCfg)).price, Toast.LENGTH_SHORT).show();

                    }
                });
                placeFragmentInContainer(newElementFragment);

//                String text = textToWriteSmthng.getText().toString();
//                textToShow.setText(text);
//                actualConfig = new AssistantUserConfig(text, "sciezka", "data aktualna", "cenahehe");
//                configList.add(actualConfig);
//                createItemToDb(configList);
            }
        });


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

    @Override
    public void onBackPressed() {
        if (fragmentContainer.getVisibility() == View.VISIBLE) {
            unhideAllViewsWithout(fragmentContainer);
        } else {
            super.onBackPressed();
        }
    }
}
