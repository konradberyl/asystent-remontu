package com.example.apps.asystentremontu.calculator;

import android.app.FragmentManager;
import android.database.Cursor;
import android.os.Bundle;

import android.support.design.widget.Snackbar;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


import com.example.apps.asystentremontu.R;
import com.orm.SugarContext;
import com.orm.SugarDb;

import java.lang.reflect.Field;
import java.util.ArrayList;

import java.util.List;


public class CalculatorActivity extends AppCompatActivity {
    RecyclerView mRecyclerView;
    CalculatorAdapter adapter;
    EditText Article,maxMoney;
    EditText Price;
    long initialCount;
    List<Articles> dataArray = new ArrayList<>();
    int modifyPos = -1;
    float sumValue = -1;
    Field field = null;
    Button summarizeButton;
    private FragmentManager fm = getFragmentManager();
    Button dlt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calculator_main_activity);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        SugarContext.init(this);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        StaggeredGridLayoutManager gridLayoutManager =
                new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        gridLayoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS);
        mRecyclerView.setLayoutManager(gridLayoutManager);
        dlt = (Button) findViewById(R.id.delete_all);
        Article = (EditText) findViewById(R.id.name_of_service);
        Price = (EditText) findViewById(R.id.price_edittext);
        maxMoney=(EditText)findViewById(R.id.type_maximum_money_editext);

        initialCount = Articles.count(Articles.class);
        summarizeButton = (Button) findViewById(R.id.button_to_summarize);
        dlt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Articles article = new Articles();
                Snackbar.make(mRecyclerView, "Usunąłeś wszystkie artykuły.", Snackbar.LENGTH_SHORT).show();
                article.deleteAll(Articles.class);
                dataArray.clear();
                adapter.notifyDataSetChanged();
                try {
                    field = SugarContext.getSugarContext().getClass().getDeclaredField("sugarDb");
                    field.setAccessible(true);
                    SugarDb db = (SugarDb) field.get(SugarContext.getSugarContext());
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
            field = SugarContext.getSugarContext().getClass().getDeclaredField("sugarDb");
            field.setAccessible(true);
            SugarDb db = (SugarDb) field.get(SugarContext.getSugarContext());
            if(db!=null){
                Cursor cursor = db.getDB().
                        rawQuery("Select Sum(price) as s from Articles", null);
                if (cursor.moveToFirst())
                    sumValue = cursor.getFloat(cursor.getColumnIndex("s"));
            }

        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }


        if (savedInstanceState != null)
            modifyPos = savedInstanceState.getInt("modify");


        if (initialCount >= 0) {

            dataArray = Articles.listAll(Articles.class);

            adapter = new CalculatorAdapter(CalculatorActivity.this, dataArray);
            mRecyclerView.setAdapter(adapter);


        }
        summarizeButton.setOnClickListener(new View.OnClickListener() {
            SummarizeDialog summarizeDialog;
            @Override
            public void onClick(View v) {

                if(sumValue!=0.0){
                    if(!maxMoney.getText().toString().isEmpty()){
                        float maxMney = Float.parseFloat(maxMoney.getText().toString());
                        if(maxMney>0){
                            summarizeDialog = new SummarizeDialog(sumValue,maxMney);
                            summarizeDialog.show(fm,"");
                        }
                        else{
                            Snackbar.make(mRecyclerView, "wprowadź poprawną kwotę!", Snackbar.LENGTH_SHORT).show();
                        }
                    }
                    else{
                        Snackbar.make(mRecyclerView, "wprowadź dostępny budżet!", Snackbar.LENGTH_SHORT).show();
                    }


                }
                else{
                    Snackbar.make(mRecyclerView, "dodaj artykuł!", Snackbar.LENGTH_SHORT).show();
                }

            }
        });


        ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {

                final int position = viewHolder.getAdapterPosition();
                final Articles article = dataArray.get(viewHolder.getAdapterPosition());
                dataArray.remove(viewHolder.getAdapterPosition());
                adapter.notifyItemRemoved(position);

                article.delete();
                initialCount -= 1;

                try {
                    field = SugarContext.getSugarContext().getClass().getDeclaredField("sugarDb");
                    field.setAccessible(true);
                    SugarDb db = (SugarDb) field.get(SugarContext.getSugarContext());
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
                                    field = SugarContext.getSugarContext().getClass().getDeclaredField("sugarDb");
                                    field.setAccessible(true);
                                    SugarDb db = (SugarDb) field.get(SugarContext.getSugarContext());
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

        adapter.SetOnItemClickListener(new CalculatorAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                Log.d("Main", "button_to_summarize = " + sumValue);
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
            Log.d("Main", "Adding new note");

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
            Snackbar.make(mRecyclerView, "Dodałeś artykuł", Snackbar.LENGTH_SHORT).show();
            try {
                field = SugarContext.getSugarContext().getClass().getDeclaredField("sugarDb");
                field.setAccessible(true);
                SugarDb db = (SugarDb) field.get(SugarContext.getSugarContext());
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
        else{
            Snackbar.make(mRecyclerView, "uzupełnij pola", Snackbar.LENGTH_SHORT).show();
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
