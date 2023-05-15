package com.example.storage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.example.storage.model.Note;
import com.example.storage.repository.DatabaseHelper;

public class UpdateNoteActivity extends AppCompatActivity {

    public EditText etNoteTitle,etNoteBody;
    public int noteId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_note);


        etNoteTitle = findViewById(R.id.etNoteTitle);
        etNoteBody = findViewById(R.id.etNoteBody);

        noteId = getIntent().getIntExtra("id",0);
        String title = getIntent().getStringExtra("title");
        String body = getIntent().getStringExtra("body");

        etNoteTitle.setText(title);
        etNoteBody.setText(body);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.update_note_top_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.btn_share_note:
                Toast.makeText(this, "Share Note", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.btn_save_note:
                updateNote();
                return true;
            case R.id.btn_delete_note:
                deleteNote();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void updateNote(){
        String noteTitle = etNoteTitle.getText().toString();
        String noteBody = etNoteBody.getText().toString();

        if (!noteBody.equals("") && !noteTitle.equals("")){
            DatabaseHelper db = new DatabaseHelper(getApplicationContext());

            db.updateNote(new Note(noteId,noteTitle,noteBody));
            Toast.makeText(this, "Note updated", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(getApplicationContext(),MainActivity.class);
            startActivity(intent);
        }

    }

    public void deleteNote(){
        String noteTitle = etNoteTitle.getText().toString();
        String noteBody = etNoteBody.getText().toString();

        DatabaseHelper db = new DatabaseHelper(getApplicationContext());

        db.deleteNote(new Note(noteId,noteTitle,noteBody));
        Toast.makeText(this, "Note Deleted", Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(intent);
    }
}