package com.example.apps.asystentremontu.calculator;

import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.apps.asystentremontu.R;
import com.squareup.picasso.Picasso;

import java.math.BigDecimal;

/**
 * Created by Beryl
 * on 04.06.2016
 */
public class SummarizeDialog extends DialogFragment {
    float sumValue;
    float restOfMoney;
    float availableMoney;

    public SummarizeDialog(float sumValue, float availableMoney) {
        this.sumValue = sumValue;
        this.availableMoney = availableMoney;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.summarize_dialog_appearance, container, false);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView sumValueTv = (TextView) view.findViewById(R.id.shopping_costs_in_summarize_dialog_tv);
        TextView leftMoneyTv = (TextView) view.findViewById(R.id.left_money_in_summarize_dialog_tv);
        TextView leftMoneySimpleText=(TextView)view.findViewById(R.id.simple_text_left_money);
        ImageView verdictImageView = (ImageView) view.findViewById(R.id.summarize_imageview);
        TextView verdictTv = (TextView) view.findViewById(R.id.verdict_in_summarize_dialog_tv);

        sumValueTv.setTextColor(getResources().getColor(R.color.red));
        sumValueTv.setText(String.valueOf(sumValue));

        restOfMoney = availableMoney - sumValue;

       // float formattedRestOfMoney = Float.parseFloat(String.format("%.0f",restOfMoney));

        if (availableMoney >= sumValue) {
            leftMoneySimpleText.setText("pozostanie: ");
            leftMoneyTv.setTextColor(getResources().getColor(R.color.green));
            leftMoneyTv.setText(String.valueOf(restOfMoney));
            Picasso.with(getActivity()).load(R.drawable.yes).fit().into(verdictImageView);
            verdictTv.setTextColor(getResources().getColor(R.color.green));
            verdictTv.setText("można kupować!");

        } else {
            leftMoneySimpleText.setText("bilans ujemny: ");
            leftMoneyTv.setTextColor(getResources().getColor(R.color.red));
            leftMoneyTv.setText(String.valueOf(round(restOfMoney,2)));
            Picasso.with(getActivity()).load(R.drawable.no).fit().into(verdictImageView);
            verdictTv.setTextColor(getResources().getColor(R.color.red));
            verdictTv.setText("zbyt drogo!");
        }

    }
    public static float round(float d, int decimalPlace) {
        BigDecimal bd = new BigDecimal(Float.toString(d));
        bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);
        return bd.floatValue();
    }
}