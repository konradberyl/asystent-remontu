package com.example.krystiano.asystentremontu;

import android.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class Fragment1 extends Fragment {
    float sumValue;
    int whichBackground;
    ImageView background;
    TextView wynik;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        View v = inflater.inflate(R.layout.activity_fragment1, container, false);
        return v;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        background =(ImageView)view.findViewById(R.id.place_to_insert_background);
         wynik = (TextView) view.findViewById(R.id.result);
        if(whichBackground==1){

            background.setImageResource(R.drawable.wynik2);
            wynik.setText(String.valueOf(sumValue));
        }
        else{
            background.setImageResource(R.drawable.wynik1);
            wynik.setText(String.valueOf(sumValue));
        }


    }

    public void setData(float sumValue, int whichBackground){
        this.sumValue = sumValue;
        this.whichBackground = whichBackground;
    }

}