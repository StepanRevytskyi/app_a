package com.example.appa.database;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;

import static com.example.appa.database.LinkDbSchema.*;

public class DataProvider extends ContentProvider {

    private static final int DATA_ID = 100;
    static final String AUTHORITY = "com.example.appa..provider.data";
    static final String DATA_PATH = "links";
    public static final Uri DATA_CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + DATA_PATH);

    private static final UriMatcher uriMatcher;

    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(AUTHORITY, DATA_PATH, DATA_ID);
    }

    private LinkBaseHelper mBaseHelper;

    @Override
    public boolean onCreate() {
        mBaseHelper = new LinkBaseHelper(getContext());
        return true;
    }

    @Override
    @NonNull
    public Cursor query(@NonNull Uri uri, @NonNull String[] projection, @NonNull String selection, @NonNull String[] selectionArgs, @NonNull String sortOrder) {
        return null;
    }

    @NonNull
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @NonNull
    @Override
    public Uri insert(@NonNull Uri uri, @NonNull ContentValues values) {
        SQLiteDatabase database = mBaseHelper.getWritableDatabase();
        long rowID = database.insert(LinkTable.NAME, null, values);
        Uri resultUri = ContentUris.withAppendedId(DATA_CONTENT_URI, rowID);
        getContext().getContentResolver().notifyChange(resultUri, null);
        return resultUri;
    }

    @Override
    public int delete(@NonNull Uri uri, @NonNull String selection, @NonNull String[] selectionArgs) {
        SQLiteDatabase database = mBaseHelper.getWritableDatabase();
        selection = "link" + " = ";
        selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
        database.delete(LinkTable.NAME, selection, selectionArgs);
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @NonNull ContentValues values, @NonNull String selection, @NonNull String[] selectionArgs) {
        SQLiteDatabase database = mBaseHelper.getWritableDatabase();
        selection = "link" + " = ";
        selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
        database.update(LinkTable.NAME, values, selection, selectionArgs);
        return 0;
    }
}
