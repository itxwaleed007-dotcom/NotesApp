package com.boots.notesapp;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "notes.db";
    private static final int DB_VERSION = 1;

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE notes (id INTEGER PRIMARY KEY AUTOINCREMENT, title TEXT, content TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS notes");
        onCreate(db);
    }

    // Naya note save karo
    public void addNote(String title, String content) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("title", title);
        values.put("content", content);
        db.insert("notes", null, values);
    }

    // Saare notes wapas do (naya sab se upar)
    public List<Note> getAllNotes() {
        List<Note> list = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT id, title, content FROM notes ORDER BY id DESC", null);
        while (cursor.moveToNext()) {
            list.add(new Note(cursor.getInt(0), cursor.getString(1), cursor.getString(2)));
        }
        cursor.close();
        return list;
    }
    // Purana note update karo
    public void updateNote(int id, String title, String content) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("title", title);
        values.put("content", content);
        db.update("notes", values, "id = ?", new String[]{String.valueOf(id)});
    }
    // Note delete karo
    public void deleteNote(int id) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete("notes", "id = ?", new String[]{String.valueOf(id)});
    }
}
