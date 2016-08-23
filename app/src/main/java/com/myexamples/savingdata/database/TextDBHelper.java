package com.myexamples.savingdata.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Sava on 8/20/2016.
 */

public class TextDBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "text.db";
    public static final int DATABASE_VERSION = 1;
    public static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + TextContract.Text.TABLE_NAME +" ("+
                    TextContract.Text._ID + " INTEGER PRIMARY KEY," +
                    TextContract.Text.TEXT + " TEXT)";

    public TextDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        createTextTable(sqLiteDatabase);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqlLiteDatabase, int i, int i1){

    }

    @Override
    public void onOpen(SQLiteDatabase db){

    }

    private void createTextTable(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    public Cursor getTexts(String id, String[] projection, String selection, String[] selectionArgs,
                           String sortOrder){
        SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
        builder.setTables(TextContract.Text.TABLE_NAME);
        if (id != null) {
            builder.appendWhere(BaseColumns._ID + " = " + id);
        }
        return builder.query(getReadableDatabase(), projection, selection, selectionArgs, null,
                null, sortOrder);
    }

    public long addText(ContentValues cv) throws SQLException {
        long id = getWritableDatabase().insert(TextContract.Text.TABLE_NAME, "", cv);
        if (id <= 0) {
            throw new SQLException("Failed to add Text");
        }
        return id;
    }
}
