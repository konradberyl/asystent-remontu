package com.example.krystiano.asystentremontu.myhistory.newelement;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.krystiano.asystentremontu.R;
import com.example.krystiano.asystentremontu.database.AssistantUserConfig;

/**
 * Created by Beryl
 * on 19.03.2016
 */
public class NewElementFragment extends Fragment {
    private ImageView placeToInsert;
    private Button createPhotoButton, addNewElement;
    private TextView actualData;
    private EditText typeDescription, typePrice;
    private String imagePath;
    private OnNewElementAdded onNewElementAdded;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.new_element_in_history, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        placeToInsert = (ImageView) view.findViewById(R.id.place_to_photo_imageview);
        createPhotoButton = (Button) view.findViewById(R.id.create_photo_button);
        actualData = (TextView) view.findViewById(R.id.actual_date_textview);
        typeDescription = (EditText) view.findViewById(R.id.type_description_edittext);
        typePrice = (EditText) view.findViewById(R.id.price_edittext);
        addNewElement = (Button) view.findViewById(R.id.add_new_element_button);

        addNewElement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AssistantUserConfig assistantUserConfig = new AssistantUserConfig(typeDescription.getText().toString(), "sciezynka", actualData.getText().toString(), typePrice.getText().toString());
                onNewElementAdded.onAdded(assistantUserConfig);
            }
        });
    }
    public void setCallbacks(OnNewElementAdded onNewElementAdded){
        this.onNewElementAdded = onNewElementAdded;
    }


}
