package com.boots.notesapp;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    FloatingActionButton fabAdd;
    RecyclerView recyclerNotes;
    TextView tvEmpty;
    List<Note> noteList = new ArrayList<>();
    NoteAdapter adapter;
    DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        fabAdd = findViewById(R.id.fabAdd);
        recyclerNotes = findViewById(R.id.recyclerNotes);
        tvEmpty = findViewById(R.id.tvEmpty);
        dbHelper = new DatabaseHelper(this);

        adapter = new NoteAdapter(noteList, note -> {
            Intent intent = new Intent(MainActivity.this, AddNoteActivity.class);
            intent.putExtra("id", note.getId());
            intent.putExtra("title", note.getTitle());
            intent.putExtra("content", note.getContent());
            startActivity(intent);
        });        recyclerNotes.setLayoutManager(new LinearLayoutManager(this));
        recyclerNotes.setAdapter(adapter);

        fabAdd.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, AddNoteActivity.class);
            startActivity(intent);
        });
    }

    // Jab bhi screen dobara samne aaye, list database se dobara bharo
    @Override
    protected void onResume() {
        super.onResume();
        noteList.clear();
        noteList.addAll(dbHelper.getAllNotes());
        adapter.notifyDataSetChanged();
        tvEmpty.setVisibility(noteList.isEmpty() ? View.VISIBLE : View.GONE);
    }
}