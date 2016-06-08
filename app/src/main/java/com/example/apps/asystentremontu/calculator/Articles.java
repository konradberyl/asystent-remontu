package com.example.apps.asystentremontu.calculator;

import com.orm.SugarRecord;


/**
 * Created by Krystiano on 2015-11-02.
 */

public class Articles extends SugarRecord {
    public String article;
    public String price;

    public Articles() {
    }

    public Articles(String article, String price) {
        this.article = article;
        this.price = price;


    }

}




