package com.example.krystiano.asystentremontu.database;

import com.raizlabs.android.dbflow.annotation.Database;

/**
 * Created by Beryl
 * on 19.03.2016
 * All rights reserved
 */
@Database(name =AppDatabase.DATABASE_NAME, version = AppDatabase.VERSION)
public class AppDatabase {
    public static final String DATABASE_NAME = "database";
    public static final int VERSION =1;
}
