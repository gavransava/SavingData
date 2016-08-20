package com.myexamples.savingdata;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.myexamples.savingdata.database.TextContract;
import com.myexamples.savingdata.database.TextDBHelper;

public class MainActivity extends AppCompatActivity {

    private static final int QUERY_ALL_VALUE = -1;
    SQLiteDatabase mDatabase;
    TextDBHelper mDBHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDBHelper = new TextDBHelper(this);
        mDatabase = mDBHelper.getWritableDatabase();
        new QueryTextTask(QUERY_ALL_VALUE).execute();
    }

    private class QueryTextTask extends AsyncTask<Void, Void, Cursor> {

        int mId;

        public QueryTextTask(int id) {
            mId = id;
        }

        @Override
        protected Cursor doInBackground(Void... voids) {
            return mDatabase.query(TextContract.Text.TABLE_NAME, null, null, null, null, null,
                    null);
        }

        @Override
        protected void onPostExecute(Cursor c) {
        }
    }
}

