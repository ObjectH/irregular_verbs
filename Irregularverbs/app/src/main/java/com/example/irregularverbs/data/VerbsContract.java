package com.example.irregularverbs.data;

import android.provider.BaseColumns;

public final class VerbsContract {
    private VerbsContract(){};

    public static final class VerbsEntry implements BaseColumns{
        public final static String TABLE_NAME = "IRREGURAL_VERBS";

        public final static String _ID = BaseColumns._ID;
        public final static String FORM_1_COLUMN = "FORM_1";
        public final static String FORM_2_COLUMN = "FORM_2";
        public final static String FORM_3_COLUMN = "FORM_3";
        public final static String TRANSLATION = "TRANSLATION";
        public final static String LINK = "LINK";
    }
}
