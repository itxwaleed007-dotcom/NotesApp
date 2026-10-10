package com.boots.notesapp;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class AddNoteActivity extends AppCompatActivity {

    TextView tvScreenTitle;
    EditText etTitle, etContent;
    Button btnSave, btnDelete;
    DatabaseHelper dbHelper;
    int noteId = -1; // -1 ka matlab naya note

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_note);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        tvScreenTitle = findViewById(R.id.tvScreenTitle);
        etTitle = findViewById(R.id.etTitle);
        etContent = findViewById(R.id.etContent);
        btnSave = findViewById(R.id.btnSave);
        btnDelete = findViewById(R.id.btnDelete);
        dbHelper = new DatabaseHelper(this);

        // Agar purana note tap karke aaye hain to edit mode
        noteId = getIntent().getIntExtra("id", -1);
        if (noteId != -1) {
            tvScreenTitle.setText("Edit Note");
            etTitle.setText(getIntent().getStringExtra("title"));
            etContent.setText(getIntent().getStringExtra("content"));
            btnSave.setText("Update Note");
            btnDelete.setVisibility(View.VISIBLE);
        }

        btnSave.setOnClickListener(view -> {
            String title = etTitle.getText().toString().trim();
            String content = etContent.getText().toString().trim();

            if (title.isEmpty() && content.isEmpty()) {
                Toast.makeText(this, "Please write something first", Toast.LENGTH_SHORT).show();
                return;
            }

            if (noteId == -1) {
                dbHelper.addNote(title, content);
                Toast.makeText(this, "Note saved", Toast.LENGTH_SHORT).show();
            } else {
                dbHelper.updateNote(noteId, title, content);
                Toast.makeText(this, "Note updated", Toast.LENGTH_SHORT).show();
            }
            finish();
        });

        btnDelete.setOnClickListener(view -> {
            new AlertDialog.Builder(this)
                    .setTitle("Delete note?")
                    .setMessage("This note will be removed permanently.")
                    .setPositiveButton("Delete", (dialog, which) -> {
                        dbHelper.deleteNote(noteId);
                        Toast.makeText(this, "Note deleted", Toast.LENGTH_SHORT).show();
                        finish();
                    })
                    .setNegativeButton("Cancel", null)
                    .show();
        });
    }
}