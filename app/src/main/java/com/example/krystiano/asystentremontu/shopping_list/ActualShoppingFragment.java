package com.example.krystiano.asystentremontu.shopping_list;

import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Message;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.krystiano.asystentremontu.R;

import org.greenrobot.eventbus.EventBus;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Beryl
 * on 12.05.2016
 */
public class ActualShoppingFragment extends DbConfigHelper {
    private ListView listView;
    private Button buttonToaddToList, buttonToAddToHistory, deleteCheckeditemsButton;
    private EditText edToWriteProductToBuy;
    private AdapterForTodayShoppingList adapterForTodayShoppingList;
    private int checkedNumber;
    public static String TAG = "ActualShoppingFragment";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.actual_shopping_list, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getItemFromDb();
        listView = (ListView) view.findViewById(R.id.today_shopping_listview);
        buttonToaddToList = (Button) view.findViewById(R.id.button_to_add_to_list);
        edToWriteProductToBuy = (EditText) view.findViewById(R.id.ed_to_write_product_to_buy);
        buttonToAddToHistory = (Button) view.findViewById(R.id.button_to_put_items_to_history);
        deleteCheckeditemsButton = (Button) view.findViewById(R.id.button_delete_checked_items);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        final String currentData = sdf.format(new Date());

        adapterForTodayShoppingList = new AdapterForTodayShoppingList();

        listView.setAdapter(adapterForTodayShoppingList);
        buttonToaddToList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!edToWriteProductToBuy.getText().toString().equals("")) {

                    GeneralShoppingDatabase.ShoppingListConfig config = new GeneralShoppingDatabase.ShoppingListConfig(edToWriteProductToBuy.getText().toString(), false, currentData);
                    ActualShoppingListSingleton.getInstance().getConfig().add(config);
                    edToWriteProductToBuy.setText("");
                    adapterForTodayShoppingList.notifyDataSetChanged();
                }

            }
        });
        buttonToAddToHistory.setOnClickListener(new View.OnClickListener() {
            boolean isClicked = false;

            @Override
            public void onClick(View v) {
                for (int i = 0; i < ActualShoppingListSingleton.getInstance().getConfig().size(); i++) {
                    if (ActualShoppingListSingleton.getInstance().getConfig().get(i).isChecked) {
                        ActualShoppingListSingleton.getInstance().getHistoryConfig().add(ActualShoppingListSingleton.getInstance().getConfig().get(i));
                        isClicked = true;

                    }
                }
                if (!isClicked) {
                    Toast.makeText(getContext(), "najpierw zaznacz elementy", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "dodano do historii", Toast.LENGTH_SHORT).show();
                    adapterForTodayShoppingList.notifyDataSetChanged();
                    EventBus.getDefault().post(new ActualShoppingFragment.Message(Message.REFLESH_ADAPTER, 1));
                    isClicked = false;
                }
            }
        });
        deleteCheckeditemsButton.setOnClickListener(new View.OnClickListener() {
            boolean isClicked = false;

            @Override
            public void onClick(View v) {
                List<GeneralShoppingDatabase.ShoppingListConfig> filteredConfigList = new ArrayList<GeneralShoppingDatabase.ShoppingListConfig>(ActualShoppingListSingleton.getInstance().getConfig());


                for (GeneralShoppingDatabase.ShoppingListConfig c : ActualShoppingListSingleton.getInstance().getConfig()) {
                    if (c.isChecked) {
                        filteredConfigList.remove(c);
                        isClicked = true;
                    }
                }
                if (!isClicked) {
                    Toast.makeText(getContext(), "najpierw zaznacz elementy", Toast.LENGTH_SHORT).show();
                } else {
                    ActualShoppingListSingleton.getInstance().setConfig(filteredConfigList);
                    refleshTodayList();
                    isClicked = false;

                }

            }
        });


    }

    public class AdapterForTodayShoppingList extends BaseAdapter {

        @Override
        public int getCount() {
            return ActualShoppingListSingleton.getInstance().getConfig().size();
        }

        @Override
        public Object getItem(int position) {
            return ActualShoppingListSingleton.getInstance().getConfig().get(position);
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
                v = inflater.inflate(R.layout.today_shopping_list_single_item, parent, false);
            }
            final TextView product = (TextView) v.findViewById(R.id.place_to_show_product_name_in_list);
            final CheckBox checkbox = (CheckBox) v.findViewById(R.id.shoping_list_checkbox);
            checkbox.setChecked(ActualShoppingListSingleton.getInstance().getConfig().get(position).isChecked);
            Log.d(TAG, "what does the isChecked say ?  -> " + ActualShoppingListSingleton.getInstance().getConfig().get(position).isChecked);

            product.setText(ActualShoppingListSingleton.getInstance().getConfig().get(position).product);
            if (checkbox.isChecked()) {
                product.setTypeface(Typeface.defaultFromStyle(Typeface.ITALIC));
                product.setPaintFlags(product.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

            } else {
                product.setPaintFlags(product.getPaintFlags() & ~Paint.STRIKE_THRU_TEXT_FLAG);
                product.setTypeface(Typeface.DEFAULT);
            }


            checkbox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (checkbox.isChecked()) {
                        ActualShoppingListSingleton.getInstance().getConfig().get(position).isChecked = true;
                        Log.d(TAG, "checkbox checked,  ifChecked =" + ActualShoppingListSingleton.getInstance().getConfig().get(position).isChecked);
                        product.setTypeface(Typeface.defaultFromStyle(Typeface.ITALIC));
                        product.setPaintFlags(product.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                        refleshTodayList();
                    } else {

                        Log.d(TAG, "checkbox unchecked,  ifChecked =" + ActualShoppingListSingleton.getInstance().getConfig().get(position).isChecked);
                        product.setPaintFlags(product.getPaintFlags() & ~Paint.STRIKE_THRU_TEXT_FLAG);
                        product.setTypeface(Typeface.DEFAULT);
                        ActualShoppingListSingleton.getInstance().getConfig().get(position).isChecked = false;
                        refleshTodayList();
                    }
                }
            });

            return v;
        }

    }

    public void refleshTodayList() {
        adapterForTodayShoppingList.notifyDataSetChanged();
    }

    @Override
    public void onDestroy() {
        createItemToDb(ActualShoppingListSingleton.getInstance().getConfig());
        createHistoryItemToDb(ActualShoppingListSingleton.getInstance().getHistoryConfig());
        super.onDestroy();
    }

    public static class Message {
        public static int REFLESH_ADAPTER = 1;

        int key;
        Object value;

        public Message(int key, Object value) {
            this.key = key;
            this.value = value;
        }


    }

}
