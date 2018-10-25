package com.example.appa;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.appa.database.LinkBaseHelper;
import com.example.appa.database.LinkCursorWrapper;
import com.example.appa.database.LinkDbSchema;
import com.example.appa.model.Link;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LinkLab {
    private static LinkLab sLinkLab;

    private Context mContext;
    private SQLiteDatabase mDatabase;

    private LinkLab(Context context) {
        mContext = context.getApplicationContext();
        mDatabase = new LinkBaseHelper(mContext).getWritableDatabase();
    }

    public List<Link> getLinks(String orderBy) {
        List<Link> links = new ArrayList<>();

        LinkCursorWrapper cursor = queryLinks(null, null, orderBy);

        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                links.add(cursor.getLinks());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }
        return links;
    }

    private LinkCursorWrapper queryLinks(String whereClause, String[] whereArgs, String orderBy) {
        Cursor cursor = mDatabase.query(
                LinkDbSchema.LinkTable.NAME,
                null,
                whereClause,
                whereArgs,
                null,
                null,
                orderBy
        );
        return new LinkCursorWrapper(cursor);
    }

    public static LinkLab get(Context context) {
        if (sLinkLab == null) {
            sLinkLab = new LinkLab(context);
        }
        return sLinkLab;
    }
}
