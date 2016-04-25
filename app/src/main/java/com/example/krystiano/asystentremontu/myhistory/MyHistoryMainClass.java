package com.example.krystiano.asystentremontu.myhistory;

<<<<<<< Updated upstream
import android.app.Activity;
=======
>>>>>>> Stashed changes
import android.os.Bundle;
import android.view.View;
<<<<<<< Updated upstream
import android.widget.EditText;
=======
import android.widget.Button;
import android.widget.FrameLayout;
>>>>>>> Stashed changes
import android.widget.TextView;

import com.example.krystiano.asystentremontu.AssistantUserConfig;
import com.example.krystiano.asystentremontu.R;
<<<<<<< Updated upstream
import com.example.krystiano.asystentremontu.database.DbManager;
import com.example.krystiano.asystentremontu.database.MyHistoryDatabase;
=======
import com.example.krystiano.asystentremontu.myhistory.actual_history.ActualHistoryFragment;
import com.example.krystiano.asystentremontu.myhistory.newelement.CommunicationWithDatabase;
import com.example.krystiano.asystentremontu.myhistory.newelement.NewElementFragment;
import com.example.krystiano.asystentremontu.myhistory.newelement.OnNewElementAdded;
>>>>>>> Stashed changes

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Beryl
 * on 19.03.2016
 * All rights reserved
 */
<<<<<<< Updated upstream
public class MyHistoryMainClass extends Activity {
    private TextView actualHistoryTextview, newElementTextview,textToShow;
    private EditText textToWriteSmthng;
    private AssistantUserConfig actualConfig;
    private ArrayList<AssistantUserConfig> configList = new ArrayList<AssistantUserConfig>();
    private List<MyHistoryDatabase> configListFromDb ;
=======
public class MyHistoryMainClass extends CommunicationWithDatabase {
    private Button actualHistoryButton, newElementButton;
    private AssistantUserConfig actualConfig;
    private ArrayList<AssistantUserConfig> configList = new ArrayList<>();
    private List<View> views = new ArrayList<>();
    private FrameLayout fragmentContainer;
    ActualHistoryFragment.RefleshRecyclerViewListener refleshRecyclerViewListener;
>>>>>>> Stashed changes

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_history);
<<<<<<< Updated upstream
        actualHistoryTextview = (TextView) findViewById(R.id.actual_history_textview);
        newElementTextview = (TextView) findViewById(R.id.new_element_textview);
        textToWriteSmthng = (EditText) findViewById(R.id.text_to_writesomething);
        textToShow=(TextView)findViewById(R.id.text_to_show);
        getItemFromDb();

        actualHistoryTextview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
=======
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

>>>>>>> Stashed changes
            }
        });
        newElementButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
<<<<<<< Updated upstream
                String text = textToWriteSmthng.getText().toString();
                textToShow.setText(text);
                actualConfig = new AssistantUserConfig(text, "sciezka", "data aktualna", "cenahehe");
                configList.add(actualConfig);
                createItemToDb(configList);
=======
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

>>>>>>> Stashed changes
            }
        });


    }

<<<<<<< Updated upstream
    private void createItemToDb(List<AssistantUserConfig> cfgs) {
        DbManager.clearAssistantDatabase();
        DbManager.saveAssistantConfig(DbManager.createAssistantConfig(cfgs));
=======
    public void setRefleshListener(ActualHistoryFragment.RefleshRecyclerViewListener refleshRecyclerViewListener){
        this.refleshRecyclerViewListener = refleshRecyclerViewListener;
    }
    public void placeFragmentInContainer(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment).commit();
>>>>>>> Stashed changes
    }

    private void getItemFromDb() {
        configListFromDb = DbManager.getAssistantConfig();
        if (!configListFromDb.isEmpty()) {
            for (MyHistoryDatabase myHistoryDatabase : configListFromDb) {
                AssistantUserConfig assistantUserConfig = new AssistantUserConfig(myHistoryDatabase.comment, myHistoryDatabase.pathToImg, myHistoryDatabase.date, myHistoryDatabase.price);
                configList.add(assistantUserConfig);
            }
            textToShow.setText(configList.get(0).comment);

        }
    }
    public static interface DeleteListener{
        public void onDelete(int position);
    }
}
