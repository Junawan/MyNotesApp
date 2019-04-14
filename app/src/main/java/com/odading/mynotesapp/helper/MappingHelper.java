package com.odading.mynotesapp.helper;

import android.database.Cursor;

import java.util.ArrayList;

import entity.Notes;

import static android.provider.BaseColumns._ID;
import static db.DatabaseContract.NoteColumns.DATE;
import static db.DatabaseContract.NoteColumns.DESCRIPTION;
import static db.DatabaseContract.NoteColumns.TITLE;

public class MappingHelper {
    public static ArrayList<Notes> mapCursorToArrayList(Cursor noteCursor) {
        ArrayList<Notes> noteList = new ArrayList<>();

        while (noteCursor.moveToNext()) {
            int id = noteCursor.getInt(noteCursor.getColumnIndexOrThrow(_ID));
            String title = noteCursor.getString(noteCursor.getColumnIndexOrThrow(TITLE));
            String description = noteCursor.getString(noteCursor.getColumnIndexOrThrow(DESCRIPTION));
            String date = noteCursor.getString(noteCursor.getColumnIndexOrThrow(DATE));
            noteList.add(new Notes(id, title, description, date));
        }
        return noteList;
    }
}
