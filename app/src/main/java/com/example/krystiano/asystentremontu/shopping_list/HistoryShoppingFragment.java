package com.example.krystiano.asystentremontu.shopping_list;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.krystiano.asystentremontu.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by Beryl
 * on 12.05.2016
 */
public class HistoryShoppingFragment extends DbConfigHelper {
    private ListView historyListView;
    private ImageView deleteIcon;
    private HistoryListViewAdapter  historyListViewAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.view_pager_lists_from_history, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
         historyListViewAdapter = new HistoryListViewAdapter();
        historyListView=(ListView)view.findViewById(R.id.listview_with_history_of_buying);
        historyListView.setAdapter(historyListViewAdapter);




    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(ActualShoppingFragment.Message message){
        if (message.key==ActualShoppingFragment.Message.REFLESH_ADAPTER){
            refleshList();
        }

    }

    @Override
    public void onAttach(Context context) {
//        if(historyListViewAdapter!=null) {
//            historyListViewAdapter.notifyDataSetChanged();
//        }
        super.onAttach(context);
    }


    public class HistoryListViewAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return ActualShoppingListSingleton.getInstance().getHistoryConfig().size();
        }

        @Override
        public Object getItem(int position) {
            return ActualShoppingListSingleton.getInstance().getHistoryConfig().get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            View v = convertView;
            if (v == null) {
                LayoutInflater inflater = getActivity().getLayoutInflater();
                v = inflater.inflate(R.layout.view_pager_lists_from_history_single_item, parent, false);
            }
            deleteIcon = (ImageView) v.findViewById(R.id.place_to_put_bin);
            deleteIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ActualShoppingListSingleton.getInstance().getHistoryConfig().remove(position);
                   // createHistoryItemToDb(ActualShoppingListSingleton.getInstance().getHistoryConfig());
                    refleshList();
                    Toast.makeText(getContext(), "usunięto", Toast.LENGTH_SHORT).show();
                }
            });
            TextView product = (TextView) v.findViewById(R.id.tv_to_place_bought_item_in_history);
            TextView data = (TextView) v.findViewById(R.id.tv_to_place_date_of_purchase_in_history);
            product.setText(ActualShoppingListSingleton.getInstance().getHistoryConfig().get(position).product);
            data.setText(ActualShoppingListSingleton.getInstance().getHistoryConfig().get(position).date);

            return v;
        }

    }
    public void refleshList(){
        historyListViewAdapter.notifyDataSetChanged();
    }

}
