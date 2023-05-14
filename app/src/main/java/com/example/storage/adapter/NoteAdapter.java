package com.example.storage.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.storage.R;
import com.example.storage.model.Note;

import java.util.ArrayList;

public class NoteAdapter extends RecyclerView.Adapter<NoteViewHolder>{

    ArrayList<Note> notes;
    Context ctx;
    public NoteAdapter(Context ctx, ArrayList<Note> notes) {
        this.ctx = ctx;
        this.notes = notes;
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(ctx);
        View listItem = inflater.inflate(R.layout.list_item,parent,false);
        return new NoteViewHolder(listItem);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        holder.tvNoteTitle.setText(notes.get(position).getTitle());
        holder.tvNoteBody.setText(notes.get(position).getBody());
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }
}

class NoteViewHolder extends RecyclerView.ViewHolder{

    public TextView tvNoteTitle,tvNoteBody;
    public NoteViewHolder(@NonNull View itemView) {
        super(itemView);

        this.tvNoteTitle = itemView.findViewById(R.id.tvNoteTitle);
        this.tvNoteBody = itemView.findViewById(R.id.tvNoteBody);
    }
}