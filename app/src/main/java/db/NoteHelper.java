package db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

import entity.Notes;

import static android.provider.BaseColumns._ID;
import static db.DatabaseContract.NoteColumns.DATE;
import static db.DatabaseContract.NoteColumns.DESCRIPTION;
import static db.DatabaseContract.NoteColumns.TITLE;
import static db.DatabaseContract.TABLE_NOTE;

public class NoteHelper {
    private static final String DATABASE_TABLE = TABLE_NOTE;
    private static DatabaseHelper databaseHelper;
    private static NoteHelper INSTANCE;
    private static SQLiteDatabase database;

    public NoteHelper(Context context) {
        databaseHelper = new DatabaseHelper(context);
    }

    public static NoteHelper getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (SQLiteOpenHelper.class) {
                if (INSTANCE == null) {
                    INSTANCE = new NoteHelper(context);
                }
            }
        }
        return INSTANCE;
    }

    public void open() throws SQLException {
        database = databaseHelper.getWritableDatabase();
    }

    public void close() {
        databaseHelper.close();

        if (database.isOpen())
            database.close();
    }

    public ArrayList<Notes> getAllNotes() {
        ArrayList<Notes> arrayList = new ArrayList<>();
        Cursor cursor = database.query(DATABASE_TABLE, null, null, null, null, null,
                _ID + " ASC", null);
        cursor.moveToFirst();
        Notes notes;
        if (cursor.getCount() > 0) {
            do {
                notes = new Notes();
                notes.setId(cursor.getInt(cursor.getColumnIndexOrThrow(_ID)));
                notes.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(TITLE)));
                notes.setDescription(cursor.getString(cursor.getColumnIndexOrThrow(DESCRIPTION)));
                notes.setDate(cursor.getString(cursor.getColumnIndexOrThrow(DATE)));

                arrayList.add(notes);
                cursor.moveToNext();
            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return arrayList;
    }

    public long insertNote(Notes notes) {
        ContentValues args = new ContentValues();
        args.put(TITLE, notes.getTitle());
        args.put(DESCRIPTION, notes.getDescription());
        args.put(DATE, notes.getDate());
        return database.insert(DATABASE_TABLE, null, args);
    }

    public int updateNote(Notes notes) {
        ContentValues args = new ContentValues();
        args.put(TITLE, notes.getTitle());
        args.put(DESCRIPTION, notes.getDescription());
        args.put(DATE, notes.getDate());
        return database.update(DATABASE_TABLE, args, _ID + "= '" + notes.getId() + "'", null);
    }

    public int deleteNote(int id) {
        return database.delete(TABLE_NOTE, _ID + " = '" + id + "'", null);
    }
}
