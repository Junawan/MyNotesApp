package com.odading.mynotesapp;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import entity.Notes;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteViewHolder> {
    private ArrayList<Notes> listNotes = new ArrayList<>();
    private Activity activity;

    public NoteAdapter(Activity activity) {
        this.activity = activity;
    }

    public ArrayList<Notes> getListNotes() {
        return listNotes;
    }

    public void setListNotes(ArrayList<Notes> listNotes) {
        if (listNotes.size() > 0) {
            this.listNotes.clear();
        }
        this.listNotes.addAll(listNotes);

        notifyDataSetChanged();
    }

    public void adItem(Notes notes) {
        this.listNotes.add(notes);
        notifyItemInserted(listNotes.size());
    }

    public void updateItem(int position, Notes notes) {
        this.listNotes.set(position, notes);
        notifyItemChanged(position, notes);
    }

    public void removeItem(int position) {
        this.listNotes.remove(position);
        notifyItemRemoved(position);

        notifyItemRangeChanged(position, listNotes.size());
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_note, parent, false);
        return new NoteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        holder.tvTitle.setText(listNotes.get(position).getTitle());
        holder.tvDescription.setText(listNotes.get(position).getDescription());
        holder.tvDate.setText(listNotes.get(position).getDate());
        holder.cvNote.setOnClickListener(new CustomOnItemClickListener(position, new CustomOnItemClickListener.OnItemClickCallback() {
            @Override
            public void onItemClicked(View view, int position) {
                Intent intent = new Intent(activity, NoteAddUpdateActivity.class);
                intent.putExtra(NoteAddUpdateActivity.EXTRA_POSITION, position);
                intent.putExtra(NoteAddUpdateActivity.EXTRA_NOTE, listNotes.get(position));

                activity.startActivityForResult(intent, NoteAddUpdateActivity.REQUEST_UPDATE);
            }
        }));
    }

    @Override
    public int getItemCount() {
        return listNotes.size();
    }

    public class NoteViewHolder extends RecyclerView.ViewHolder {
        final TextView tvTitle, tvDescription, tvDate;
        final CardView cvNote;

        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_item_title);
            tvDescription = itemView.findViewById(R.id.tv_item_description);
            tvDate = itemView.findViewById(R.id.tv_item_date);
            cvNote = itemView.findViewById(R.id.cv_item_note);
        }
    }
}
