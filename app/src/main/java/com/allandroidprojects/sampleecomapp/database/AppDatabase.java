package com.allandroidprojects.sampleecomapp.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

@Database(entities = CartDatabase.class, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase INSTANCE;



    public abstract CartDoa cartDoa();
    public static AppDatabase getInstance(Context context) {
        if (null == INSTANCE) {
            INSTANCE = getAppDatabase(context);
        }
        return INSTANCE;
    }
    public static AppDatabase getAppDatabase(Context context) {

            return Room.databaseBuilder(context.getApplicationContext()
                    , AppDatabase.class, "cartlist_table-database").allowMainThreadQueries().build();

    }

    @Override
    public void close() {
        super.close();
    }
    public static void destroyInstance() {
        INSTANCE = null;
    }
}
