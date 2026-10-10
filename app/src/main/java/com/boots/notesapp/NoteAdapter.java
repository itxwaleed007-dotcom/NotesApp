package com.boots.notesapp;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteViewHolder> {

    public interface OnNoteClickListener {
        void onNoteClick(Note note);
    }

    private final List<Note> notes;
    private final OnNoteClickListener listener;

    public NoteAdapter(List<Note> notes, OnNoteClickListener listener) {
        this.notes = notes;
        this.listener = listener;
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_note, parent, false);
        return new NoteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        Note note = notes.get(position);
        holder.tvTitle.setText(note.getTitle());
        holder.tvContent.setText(note.getContent());
        holder.itemView.setOnClickListener(v -> listener.onNoteClick(note));
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    static class NoteViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvContent;

        NoteViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvNoteTitle);
            tvContent = itemView.findViewById(R.id.tvNoteContent);
        }
    }
}