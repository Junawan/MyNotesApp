package com.odading.mynotesapp;

import android.view.View;

public class CustomOnItemClickListener implements View.OnClickListener {
    private int position;
    private OnItemClickCallback onItemClickCallbacck;

    public CustomOnItemClickListener(int position, OnItemClickCallback onItemClickCallbacck) {
        this.position = position;
        this.onItemClickCallbacck = onItemClickCallbacck;
    }
    @Override
    public void onClick(View v) {
        onItemClickCallbacck.onItemClicked(v, position);
    }

    public interface OnItemClickCallback {
        void onItemClicked(View view, int position);
    }
}
