package com.myexamples.savingdata;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

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
        TextView textView = (TextView) findViewById(R.id.textView3);
        textView.setMovementMethod(new ScrollingMovementMethod());
        new QueryTextTask(QUERY_ALL_VALUE).execute();
    }

    public void onClick(View view){
        EditText editText = (EditText)findViewById(R.id.editText);
        String text = editText.getText().toString();
        insertText(text);
    }

    public void insertText(String text) {
        ContentValues cv = new ContentValues();
        cv.put(TextContract.Text.TEXT, text);
        mDatabase.insert(TextContract.Text.TABLE_NAME, null, cv);
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
            if(mId == QUERY_ALL_VALUE) {
                if (c.getCount() < 1)
                    return;

                c.moveToLast();
                StringBuilder textToDisplay = new StringBuilder(c.getString(c.getColumnIndex(TextContract.Text.TEXT)));
                textToDisplay.append("\n\n");
                while(c.moveToPrevious()){
                    textToDisplay.append(c.getString(c.getColumnIndex(TextContract.Text.TEXT))).append("\n\n");
                }
                c.close();
                TextView textView = (TextView) findViewById(R.id.textView3);
                textView.setText(textToDisplay);
            }
        }
    }
}

