package com.example.krystiano.asystentremontu.myhistory.actual_history;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.krystiano.asystentremontu.R;
import com.example.krystiano.asystentremontu.database.AssistantUserConfig;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.List;

/**
 * Created by Beryl
 * on 03.04.2016
 */
public class AdapterForRecyclerView extends RecyclerView.Adapter<AdapterForRecyclerView.ViewHolder> {

    List<AssistantUserConfig> configToRecyclerView;
    private Context context;
    private File file;
   private Picasso.Builder builder;

    public AdapterForRecyclerView(Context context) {
        this.context = context;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
         builder = new Picasso.Builder(context);
        builder.listener(new Picasso.Listener() {
            @Override
            public void onImageLoadFailed(Picasso picasso, Uri uri, Exception exception) {
                exception.printStackTrace();
            }
        });
        //  ActualHistoryFragment actualHistoryFragment = new ActualHistoryFragment();
        // context = actualHistoryFragment.getActivity().getApplicationContext();
        return viewHolder;
    }

    public void setConfig(List<AssistantUserConfig> config) {
        configToRecyclerView = config;
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.priceTv.setText(configToRecyclerView.get(position).price);
        holder.commentTv.setText(configToRecyclerView.get(position).comment);
        holder.dateTv.setText(configToRecyclerView.get(position).date);
        Log.i("AdapterForRecyclerView ", " positions in onBindViewHolder: " + position);
        file = new File(configToRecyclerView.get(position).pathToImg);
        builder.build().load(file).fit().into(holder.placeToInsertPhotoInHistory);
//        Picasso.with(context).load(new File(configToRecyclerView.get(position).pathToImg))
//                .fit()
//                .placeholder(R.drawable.kostka)
//                .error(R.drawable.icon)
//                .into(holder.placeToInsertPhotoInHistory);


    }

    @Override
    public int getItemCount() {
        return configToRecyclerView.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        public TextView priceTv, commentTv, dateTv;
        public ImageView placeToInsertPhotoInHistory;

        public ViewHolder(View itemView) {
            super(itemView);
            this.priceTv = (TextView) itemView.findViewById(R.id.textview_with_prize_in_history);
            this.commentTv = (TextView) itemView.findViewById(R.id.textview_with_description_in_history);
            this.dateTv = (TextView) itemView.findViewById(R.id.textview_with_date_in_history);
            this.placeToInsertPhotoInHistory = (ImageView) itemView.findViewById(R.id.place_to_insert_photo_in_history_imageview);
        }
    }
}
