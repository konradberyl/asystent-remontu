package com.example.krystiano.asystentremontu;

import android.app.Activity;
import android.content.Context;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class kalkulatorAdapter extends ArrayAdapter<kalkulatorRowBean> {

    Context context;
    int layoutResourceId;
    kalkulatorRowBean data[] = null;

    public kalkulatorAdapter(Context context, int layoutResourceId, kalkulatorRowBean[] data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;

    }
    @Override

    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        RowBeanHolder holder = null;
        if (row == null)
        {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);
            holder = new RowBeanHolder();
            holder.button = (Button) row.findViewById(R.id.add_button);
            holder.textView = (TextView) row.findViewById(R.id.name_of_service);
            holder.editText=(EditText)row.findViewById(R.id.type_quantity_edittext);
            holder.editText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
            row.setTag(holder);
            }
        else {
            holder = (RowBeanHolder) row.getTag();
            }
        kalkulatorRowBean object = data[position];
        holder.textView.setText(object.usluga);

        return row;
        }
    static class RowBeanHolder


    {
        Button button;
        TextView textView;
        EditText editText;
        }
}