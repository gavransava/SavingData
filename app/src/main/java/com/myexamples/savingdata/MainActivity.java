package com.myexamples.savingdata;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.myexamples.savingdata.database.TextContract;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView textView = (TextView) findViewById(R.id.textView3);
        textView.setMovementMethod(new ScrollingMovementMethod());
        new QueryTextTask(TextContract.Text.CONTENT_URI).execute();
    }

    public void onClick(View view){
        EditText editText = (EditText)findViewById(R.id.editText);
        String text = editText.getText().toString();
        insertText(text);
    }

    public void insertText(String text) {
        ContentValues cv = new ContentValues();
        cv.put(TextContract.Text.TEXT, text);
        getContentResolver().insert(TextContract.Text.CONTENT_URI, cv);
        new QueryTextTask(TextContract.Text.CONTENT_URI).execute();
    }

    private class QueryTextTask extends AsyncTask<Void, Void, Cursor> {

        Uri mUri;

        public QueryTextTask(Uri uri) {
            mUri = uri;
        }

        @Override
        protected Cursor doInBackground(Void... voids) {
            return getContentResolver().query(mUri, null, null, null, null);
        }

        @Override
        protected void onPostExecute(Cursor c) {
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

