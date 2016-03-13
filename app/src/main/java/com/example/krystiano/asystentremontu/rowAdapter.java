package com.example.krystiano.asystentremontu;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Krystiano on 2015-10-30.
 */
public class rowAdapter extends ArrayAdapter <rowBean>{

    Context context;
    int layoutResourceId;
    rowBean data[] = null;

    public rowAdapter(Context context, int layoutResourceId, rowBean[] data){
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;

    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        rowBeanHolder holder = null;

        if(row == null)
        {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);

            holder = new rowBeanHolder();
            holder.imgIcon = (ImageView)row.findViewById(R.id.imgIcon);
            holder.txtTitle = (TextView)row.findViewById(R.id.txtTitle);
            holder.txtTitle1 = (TextView)row.findViewById(R.id.txtTitle1);
            row.setTag(holder);
        }
        else
        {
            holder = (rowBeanHolder)row.getTag();
        }

        rowBean object = data[position];
        holder.txtTitle.setText(object.title);
        holder.imgIcon.setImageResource(object.icon);
        holder.txtTitle1.setText(object.prize);
        return row;
    }

static class rowBeanHolder
{
    ImageView imgIcon;
    TextView txtTitle;
    TextView txtTitle1;



}
}