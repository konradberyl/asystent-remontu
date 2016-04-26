package com.example.krystiano.asystentremontu.database;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

/**
 * Created by Beryl
 * on 19.03.2016
 */
@Table(name = AppDatabase.DATABASE_NAME,database = AppDatabase.class)
public class MyHistoryDatabase extends BaseModel {
    @Column
    @PrimaryKey
    public String comment;

    @Column
    public String pathToImg;

    @Column
    public String date;

    @Column
    public String price;
}
