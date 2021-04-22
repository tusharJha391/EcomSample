package com.allandroidprojects.sampleecomapp.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.IOException;

public class SqlLiteDataBase {
    private final DatabaseOpenHelper databaseOpenHelper;
    private static final String TAG = "SearchResultDatabase";
    public static final String COL_WORD = "WORD";
    public static final String COL_DEFINITION = "DEFINITION";

    private static final String DATABASE_NAME = "SEARCHABLE";
    private static final String FTS_VIRTUAL_TABLE = "FTS";
    private static final int DATABASE_VERSION = 1;

    public SqlLiteDataBase(Context context) {
        databaseOpenHelper = new DatabaseOpenHelper(context);
    }

    private static class DatabaseOpenHelper extends SQLiteOpenHelper {

        private final Context helperContext;
        private SQLiteDatabase mDatabase;

        private static final String FTS_TABLE_CREATE =
                "CREATE VIRTUAL TABLE " + FTS_VIRTUAL_TABLE +
                        " USING fts3 (" +
                        COL_WORD + ", " +
                        COL_DEFINITION + ")";

        private DatabaseOpenHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
            helperContext = context;
        }


        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            mDatabase=sqLiteDatabase;
            mDatabase.execSQL(FTS_TABLE_CREATE);

        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
            Log.w(TAG, "Upgrading database from version " + i + " to "
                    + i1 + ", which will destroy all old data");
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + FTS_VIRTUAL_TABLE);
            onCreate(sqLiteDatabase);

        }

        public void loadData() {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        LoadDataFinally();

                    } catch (IOException e) {

                    }


                }

                private void LoadDataFinally() throws IOException {



                }
            });

        }

    }
}
