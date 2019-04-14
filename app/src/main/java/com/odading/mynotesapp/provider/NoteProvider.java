package com.odading.mynotesapp.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.odading.mynotesapp.MainActivity;

import java.util.logging.LogRecord;

import db.NoteHelper;

import static db.DatabaseContract.AUTHORITY;
import static db.DatabaseContract.NoteColumns.CONTENT_URI;
import static db.DatabaseContract.NoteColumns.TABLE_NAME;

public class NoteProvider extends ContentProvider {
    private static final int NOTE = 1;
    private static final int NOTE_ID = 2;
    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    private NoteHelper noteHelper;

    static {
        sUriMatcher.addURI(AUTHORITY, TABLE_NAME, NOTE);
        sUriMatcher.addURI(AUTHORITY, TABLE_NAME + "/#", NOTE_ID);
    }

    @Override
    public boolean onCreate() {
        noteHelper = NoteHelper.getInstance(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] strings, @Nullable String s,
                        @Nullable String[] strings1, @Nullable String s1) {
        noteHelper.open();
        Cursor cursor;
        switch (sUriMatcher.match(uri)) {
            case NOTE:
                cursor = noteHelper.queryProvider();
                break;
            case NOTE_ID:
                cursor = noteHelper.queryByIdProvider(uri.getLastPathSegment());
                break;
            default:
                cursor = null;
                break;
        }
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        noteHelper.open();
        long added;
        switch (sUriMatcher.match(uri)) {
            case NOTE:
                added = noteHelper.insertProvider(contentValues);
                break;
            default:
                added = 0;
                break;
        }

        getContext().getContentResolver().notifyChange(CONTENT_URI, new MainActivity.DataObserver(new Handler(), getContext()));

        return Uri.parse(CONTENT_URI + "/" + added);
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        noteHelper.open();
        int delete;
        switch (sUriMatcher.match(uri)) {
            case NOTE_ID:
                delete = noteHelper.deleteProvider(uri.getLastPathSegment());
                break;
            default:
                delete = 0;
                break;
        }
        getContext().getContentResolver().notifyChange(CONTENT_URI, new MainActivity.DataObserver(new android.os.Handler(), getContext()));

        return delete;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        noteHelper.open();
        int update;
        switch (sUriMatcher.match(uri)) {
            case NOTE_ID:
                update = noteHelper.updateProvider(uri.getLastPathSegment(), contentValues);
                break;
            default:
                update = 0;
                break;
        }
        getContext().getContentResolver().notifyChange(CONTENT_URI, new MainActivity.DataObserver(new Handler(), getContext()));
        return update;
    }
}
