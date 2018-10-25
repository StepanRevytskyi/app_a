package com.example.appa.database;

import android.database.Cursor;
import android.database.CursorWrapper;
import com.example.appa.model.Link;
import java.util.Date;
import static com.example.appa.database.LinkDbSchema.*;

public class LinkCursorWrapper extends CursorWrapper {

    public LinkCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public Link getLinks() {
        String link = getString(getColumnIndex(LinkTable.Cols.LINK));
        String status = getString(getColumnIndex(LinkTable.Cols.STATUS));
        long date = getLong(getColumnIndex(LinkTable.Cols.TIME_TO_OPEN));

        Link mLink = new Link();
        mLink.setLink(link);
        mLink.setStatus(status);
        mLink.setDate(new Date(date));

        return mLink;
    }
}
