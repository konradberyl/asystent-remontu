package com.example.krystiano.asystentremontu;

import android.app.Activity;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.database.Cursor;
import android.os.Bundle;

import android.support.design.widget.Snackbar;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.orm.SugarContext;
import com.orm.SugarDb;

import java.lang.reflect.Field;
import java.util.ArrayList;

import java.util.List;

public class kalkulator extends AppCompatActivity {
    RecyclerView mRecyclerView;
    kalkulatorAdapter adapter;
    //ImageView delete;
    EditText Article;
    EditText Price;
    long initialCount;
    List<Articles> dataArray = new ArrayList<>();
    int modifyPos = -1;
    float sumValue = -1;
    Field f = null;
    Button b1;
    Fragment1 F1;
    FragmentTransaction FT;
    Button dlt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listview_items);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        // mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        StaggeredGridLayoutManager gridLayoutManager =
                new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        gridLayoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS);
        mRecyclerView.setLayoutManager(gridLayoutManager);
        dlt=(Button)findViewById(R.id.delete_all);
        Article = (EditText) findViewById(R.id.name_of_service);
        Price = (EditText) findViewById(R.id.price_edittext);

        initialCount = Articles.count(Articles.class);
        b1=(Button)findViewById(R.id.sum);
    dlt.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            Articles article = new Articles();
            Snackbar.make(mRecyclerView, "Usunąłeś wszystkie artykuły.", Snackbar.LENGTH_LONG).show();
            article.deleteAll(Articles.class);
            dataArray.clear();
            adapter.notifyDataSetChanged();
            try {
                f = SugarContext.getSugarContext().getClass().getDeclaredField("sugarDb");
                f.setAccessible(true);
                SugarDb db = (SugarDb) f.get(SugarContext.getSugarContext());
                Cursor cursor = db.getDB().
                        rawQuery("Select Sum(price) as s from Articles", null);
                if (cursor.moveToFirst())
                    sumValue = cursor.getFloat(cursor.getColumnIndex("s"));

            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    });
        try {
            f = SugarContext.getSugarContext().getClass().getDeclaredField("sugarDb");
            f.setAccessible(true);
            SugarDb db = (SugarDb) f.get(SugarContext.getSugarContext());
            Cursor cursor = db.getDB().
                    rawQuery("Select Sum(price) as s from Articles", null);
            if (cursor.moveToFirst())
                sumValue = cursor.getFloat(cursor.getColumnIndex("s"));

        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }


        if (savedInstanceState != null)
            modifyPos = savedInstanceState.getInt("modify");


        if (initialCount >= 0) {

            dataArray = Articles.listAll(Articles.class);

            adapter = new kalkulatorAdapter(kalkulator.this, dataArray);
            mRecyclerView.setAdapter(adapter);

            if (dataArray.isEmpty())
                Snackbar.make(mRecyclerView, "Nie masz zadnego artykulu.", Snackbar.LENGTH_LONG).show();

        }
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick( View v) {
                FragmentManager FM = getFragmentManager();
                 FT = FM.beginTransaction();
                 F1 = new Fragment1();
               // wynik.setText(String.valueOf(sumValue));
                if(sumValue<500) {
                    F1.setData(sumValue, 1);

                  // FT.addToBackStack("null");

               }
                else{
                    //Fragment2 F2 = new Fragment2();
                    F1.setData(sumValue, 2);
                    //FT.addToBackStack("null");
                }
                FT.add(R.id.podsumuj, F1);
                FT.addToBackStack(null);
                FT.commit();

            }
        });


        // Handling swipe to delete
        ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                //Remove swiped item from list and notify the RecyclerView

                final int position = viewHolder.getAdapterPosition();
                final Articles article = dataArray.get(viewHolder.getAdapterPosition());
                dataArray.remove(viewHolder.getAdapterPosition());
                adapter.notifyItemRemoved(position);

                article.delete();
                initialCount -= 1;

                try {
                    f = SugarContext.getSugarContext().getClass().getDeclaredField("sugarDb");
                    f.setAccessible(true);
                    SugarDb db = (SugarDb) f.get(SugarContext.getSugarContext());
                    Cursor cursor = db.getDB().
                            rawQuery("Select Sum(price) as s from Articles", null);
                    if (cursor.moveToFirst())
                        sumValue = cursor.getFloat(cursor.getColumnIndex("s"));

                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }


                Snackbar.make(mRecyclerView, "Artykuł usunięty", Snackbar.LENGTH_SHORT)
                        .setAction("COFNIJ", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                article.save();
                                dataArray.add(position, article);
                                adapter.notifyItemInserted(position);
                                initialCount += 1;
                                try {
                                    f = SugarContext.getSugarContext().getClass().getDeclaredField("sugarDb");
                                    f.setAccessible(true);
                                    SugarDb db = (SugarDb) f.get(SugarContext.getSugarContext());
                                    Cursor cursor = db.getDB().
                                            rawQuery("Select Sum(price) as s from Articles", null);
                                    if (cursor.moveToFirst())
                                        sumValue = cursor.getFloat(cursor.getColumnIndex("s"));

                                } catch (NoSuchFieldException e) {
                                    e.printStackTrace();
                                } catch (IllegalAccessException e) {
                                    e.printStackTrace();
                                }
                            }
                        })
                        .show();
            }

        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(mRecyclerView);

        adapter.SetOnItemClickListener(new kalkulatorAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                Log.d("Main", "sauma wynosi" + sumValue);
                Snackbar.make(mRecyclerView, "Aby usunąc pozycje przesuń w lewo lub w prawo", Snackbar.LENGTH_SHORT).show();
                modifyPos = position;

            }
        });


    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt("modify", modifyPos);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        modifyPos = savedInstanceState.getInt("modify");
    }

    @Override
    protected void onResume() {
        super.onResume();

        final long newCount = Articles.count(Articles.class);

        if (newCount > initialCount) {
            // A note is added
            Log.d("Main", "Adding new note");

            // Just load the last added note (new)
            Articles article = Articles.last(Articles.class);

            dataArray.add(article);
            adapter.notifyItemInserted((int) newCount);

            initialCount = newCount;
        }

        if (modifyPos != -1) {
            dataArray.set(modifyPos, Articles.listAll(Articles.class).get(modifyPos));
            adapter.notifyItemChanged(modifyPos);
        }

    }


    public void addNewRow(View view) {
        final String newArticle = Article.getText().toString();
        final String newPrice = Price.getText().toString();

        if (!newArticle.isEmpty() && !newPrice.isEmpty()) {


            final Articles article = new Articles(newArticle, newPrice);
            article.save();
            dataArray.add(article);
            adapter.notifyDataSetChanged();
            Snackbar.make(mRecyclerView, "Dodałeś artykuł", Snackbar.LENGTH_LONG).show();
            try {
                f = SugarContext.getSugarContext().getClass().getDeclaredField("sugarDb");
                f.setAccessible(true);
                SugarDb db = (SugarDb) f.get(SugarContext.getSugarContext());
                Cursor cursor = db.getDB().
                        rawQuery("Select Sum(price) as s from Articles", null);
                if (cursor.moveToFirst())
                    sumValue = cursor.getFloat(cursor.getColumnIndex("s"));

            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }




            Article.setText("");
            Price.setText("");

        }
    }



   @Override
    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() > 0) {
            getFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
    }
}
