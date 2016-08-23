package com.myexamples.savingdata.contentprovider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.Nullable;

import com.myexamples.savingdata.database.TextContract;
import com.myexamples.savingdata.database.TextDBHelper;

/**
 * Created by Sava on 8/23/2016.
 */
public class TextProvider extends ContentProvider{

    public static final int TEXT = 1;
    public static final int TEXTS = 2;
    public static final String AUTHORITY = "com.myexamples.provider.text";
    public static final UriMatcher sUriMatcher = getUriMatcher();

    private TextDBHelper mDbHelper;

    private static UriMatcher getUriMatcher() {
        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(AUTHORITY, TextContract.Text.TABLE_NAME, TEXTS);
        uriMatcher.addURI(AUTHORITY, TextContract.Text.TABLE_NAME + "/#", TEXT);
        return uriMatcher;
    }

    @Override
    public boolean onCreate() {
        mDbHelper = new TextDBHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs,
                        String sortOrder) {
        String id = null;
        if (sUriMatcher.match(uri) == TEXT) {
            id = uri.getLastPathSegment();
        }
        return mDbHelper.getTexts(id, projection, selection, selectionArgs, sortOrder);
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        try {
            long id = mDbHelper.addText(contentValues);
            return ContentUris.withAppendedId(TextContract.Text.CONTENT_URI, id);
        } catch (Exception e) {
            return null;
        }
    }

    @Nullable
    @Override
    public String getType(Uri uri){
        return null;
    }

    @Override
    public int delete(Uri uri, String s, String[] strings) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String s, String[] strings) {
        return 0;
    }
}
