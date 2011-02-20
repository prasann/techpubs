package com.techpubs;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

public class ContactDbAdapter {

    public static final String KEY_NAME = "name";
    public static final String KEY_SCORE = "score";
    public static final String KEY_ROWID = "_id";

    private static final String DATABASE_CREATE = "create table contactdetails (_id integer primary key autoincrement,"
            + "name text not null, score integer not null);";

    private static final String DATABASE_NAME = "contacts";
    private static final String DATABASE_TABLE = "contactdetails";
    private static final int DATABASE_VERSION = 3;

    public static final String TAG = "ContactDbAdapter";

    private final Context mCtx;
    private DatabaseHelper mDbHelper;
    private SQLiteDatabase mDb;

    private static class DatabaseHelper extends SQLiteOpenHelper {
        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {

            db.execSQL(DATABASE_CREATE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
                    + newVersion + ", which will destroy all old data");
            db.execSQL("DROP TABLE IF EXISTS contactdetails");
            onCreate(db);
        }

    }

    public ContactDbAdapter open() throws SQLiteException {
        mDbHelper = new DatabaseHelper(mCtx);
        try {
            mDb = mDbHelper.getWritableDatabase();
        } catch (SQLiteException ex) {
            mDb = mDbHelper.getReadableDatabase();
        }
        return this;
    }

    public ContactDbAdapter(Context ctx) {
        this.mCtx = ctx;
    }

    public void close() {
        mDbHelper.close();
    }

    public long createContact(String name, int score) {
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_NAME, name);
        initialValues.put(KEY_SCORE, score);

        return mDb.insert(DATABASE_TABLE, null, initialValues);
    }

    public int updateEntry(long _rowIndex, String name, int score) {

        String where = KEY_ROWID + "=" + _rowIndex;

        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_NAME, name);
        initialValues.put(KEY_SCORE, score);

        // TODO fill in the ContentValue based on the new object
        return mDb.update(DATABASE_TABLE, initialValues, where, null);
    }

    public boolean deleteContact(long rowId) {
        Toast.makeText(this.mCtx, "RowID:" + rowId, Toast.LENGTH_LONG).show();
        return mDb.delete(DATABASE_TABLE, KEY_ROWID + "=" + rowId, null) > 0;
    }

    public Cursor fetchAllContacts() {
        String order = KEY_SCORE + " DESC ";
        return mDb.query(DATABASE_TABLE, new String[]{KEY_ROWID, KEY_NAME,
                KEY_SCORE}, null, null, null, null, order);
    }

}