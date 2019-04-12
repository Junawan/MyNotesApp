package com.odading.mynotesapp;

import java.util.ArrayList;

import entity.Notes;

public interface LoadNotesCallback {
    void preExecute();

    void postExecute(ArrayList<Notes> notes);
}
