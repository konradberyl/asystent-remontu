package com.example.krystiano.asystentremontu;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

// * Created by Krystiano on 2016-04-23.
public class kalkulatorAdapter extends RecyclerView.Adapter<kalkulatorAdapter.ArticlesVH> {
    Context context;
    List<Articles> dataArray;

    OnItemClickListener clickListener;

    public kalkulatorAdapter(Context context, List<Articles> dataArray) {
        this.context = context;
        this.dataArray = dataArray;

    }


    @Override
    public ArticlesVH onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.content_kalkulator, parent, false);
        ArticlesVH viewHolder = new ArticlesVH(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ArticlesVH holder, int position) {

        holder.titleTextView.setText(dataArray.get(position).article);
        holder.editionTextView.setText(dataArray.get(position).price);

    }

    @Override
    public int getItemCount() {
        return dataArray.size();
    }

    class ArticlesVH extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView titleTextView;
        TextView editionTextView;

        public ArticlesVH(View itemView) {
            super(itemView);

            titleTextView = (TextView) itemView.findViewById(R.id.service);
            editionTextView = (TextView) itemView.findViewById(R.id.pricelist);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            clickListener.onItemClick(v, getAdapterPosition());
        }
    }

    public interface OnItemClickListener {
        public void onItemClick(View view, int position);
    }

    public void SetOnItemClickListener(final OnItemClickListener itemClickListener) {
        this.clickListener = itemClickListener;
    }

}
