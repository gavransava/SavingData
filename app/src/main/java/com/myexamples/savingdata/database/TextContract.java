package com.myexamples.savingdata.database;

import android.provider.BaseColumns;

/**
 * Created by Sava on 8/20/2016.
 */

public class TextContract {

    public TextContract() {}

    public static abstract class Text implements BaseColumns {
        public static final String TABLE_NAME = "text_database";
        public static final String TEXT = "text";
    }
}
