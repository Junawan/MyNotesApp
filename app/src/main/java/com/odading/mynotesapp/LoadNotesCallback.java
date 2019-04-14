package com.odading.mynotesapp;

import android.database.Cursor;

import java.util.ArrayList;

import entity.Notes;

public interface LoadNotesCallback {
    void preExecute();

    void postExecute(Cursor notes);
}
