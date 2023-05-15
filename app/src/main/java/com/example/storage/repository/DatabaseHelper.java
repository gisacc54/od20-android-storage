package com.example.storage.repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.storage.model.Note;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "note_app";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NAME = "notes";

    private static final String KEY_ID = "id";
    private static final String KEY_TITLE = "title";
    private static final String KEY_BODY = "body";

    public DatabaseHelper(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "create table "+TABLE_NAME+" ( "+KEY_ID+" integer primary key,"+KEY_TITLE+" text,"+KEY_BODY+" text )";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        String query = "drop table if exists "+TABLE_NAME;
        db.execSQL(query);

        onCreate(db);
    }

    public void createNote(Note note){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(KEY_TITLE,note.getTitle());
        values.put(KEY_BODY,note.getBody());

        db.insert(TABLE_NAME,null,values);
        db.close();
    }

    public ArrayList<Note> getAllNotes(){
        ArrayList<Note> notes = new ArrayList<>();

        String query = "select * from "+TABLE_NAME+" order by id desc";

        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(query,null);

        if (cursor.moveToFirst()){
            do {
                Note note = new Note();
                note.setId(Integer.parseInt(cursor.getString(0)));
                note.setTitle(cursor.getString(1));
                note.setBody(cursor.getString(2));
                notes.add(note);
            }while (cursor.moveToNext());
        }

        return notes;
    }

    public int updateNote(Note note){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_TITLE,note.getTitle());
        values.put(KEY_BODY,note.getBody());

        int status = db.update(TABLE_NAME,values,KEY_ID+"=?",new String[]{String.valueOf(note.getId())});
        db.close();
        return status;
    }

    public void deleteNote(Note note){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME,KEY_ID+"=?",new String[]{String.valueOf(note.getId())});
    }

}
