package com.example.apps.asystentremontu.shopping_list;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;

import com.example.apps.asystentremontu.R;
import com.viewpagerindicator.TitlePageIndicator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Beryl
 * on 12.05.2016
 */
public class ShoppingListMainActivity extends FragmentActivity {
    private ViewPager viewPager;
    private PagerAdapter pagerAdapter;
    private static final int NUM__PAGES = 2;
    private List<Fragment> listOfFragments = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shopping_list_main_activity);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        pagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);
        TitlePageIndicator titlePageIndicator = (TitlePageIndicator) findViewById(R.id.titles);
        titlePageIndicator.setViewPager(viewPager);
        pagerAdapter.notifyDataSetChanged();
        ActualShoppingFragment actualShoppingFragment = new ActualShoppingFragment();
        HistoryShoppingFragment historyShoppingFragment = new HistoryShoppingFragment();
        listOfFragments.add(actualShoppingFragment);
        listOfFragments.add(historyShoppingFragment);


    }

    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            //pagerAdapter.notifyDataSetChanged();
            return listOfFragments.get(position);
        }

        @Override
        public int getCount() {
            return NUM__PAGES;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            String text;
            switch (position) {
                case 0:

                    text = "dzisiaj";
                    return text;
                case 1:
                    text = "historia";
                    return text;
                default:
                    return "default";
            }
        }
    }

}
