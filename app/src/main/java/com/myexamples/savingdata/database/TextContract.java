package com.myexamples.savingdata.database;

import android.net.Uri;
import android.provider.BaseColumns;

import com.myexamples.savingdata.contentprovider.TextProvider;

/**
 * Created by Sava on 8/20/2016.
 */

public class TextContract {

    public TextContract() {}

    public static abstract class Text implements BaseColumns {
        public static final Uri CONTENT_URI = Uri
                .parse("content://" + TextProvider.AUTHORITY + "/" + TextContract.Text.TABLE_NAME);
        public static final String TABLE_NAME = "text_database";
        public static final String TEXT = "text";
    }
}
