package com.example.krystiano.asystentremontu;

import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.orm.SugarRecord;
import com.orm.annotation.Table;


/**
 * Created by Krystiano on 2015-11-02.
 */

public class Articles extends SugarRecord {
        public String article;
        public String price;
        //public long Id;
public Articles(){}

        public Articles(String article, String price) {
           // this.Id=Id;
            this.article = article;
            this.price = price;


        }

    }




