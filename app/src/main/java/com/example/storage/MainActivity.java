package com.example.storage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.storage.adapter.NoteAdapter;
import com.example.storage.model.Note;
import com.example.storage.repository.DatabaseHelper;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,NoteAdapter.OnClickListener {

    public static final String darkTheme = "Dark";
    public static final String lightTheme = "Light";
    public RecyclerView noteRecycleView;
    public ArrayList<Note> notes = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        noteRecycleView = findViewById(R.id.noteRecycleView);

        getNotes();

        NoteAdapter adapter = new NoteAdapter(this,notes);

        adapter.setOnClickListener(this);

        noteRecycleView.setHasFixedSize(true);
        noteRecycleView.setLayoutManager(new StaggeredGridLayoutManager(2,LinearLayoutManager.VERTICAL));
        noteRecycleView.setAdapter(adapter);


        setSavedTheme();
    }


    @Override
    public void onClick(View view) {

    }
    @Override
    public void onClick(int position, Note note) {
        Toast.makeText(this, "Position: "+position+" id: "+note.getId(), Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(this,UpdateNoteActivity.class);
        intent.putExtra("id",note.getId());
        intent.putExtra("title",note.getTitle());
        intent.putExtra("body",note.getBody());
        startActivity(intent);

    }

    public void getNotes(){
        DatabaseHelper db = new DatabaseHelper(getApplicationContext());
        notes = db.getAllNotes();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.top_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.btn_theme_dark:
                changeTheme(lightTheme);
                savaTheme(lightTheme);
                return true;
            case R.id.btn_theme_light:
                changeTheme(darkTheme);
                savaTheme(darkTheme);
                return true;
            case R.id.btn_create_note:
                Toast.makeText(this, "Create Note", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this,CreateNoteActivity.class);
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    public void changeTheme(String theme){
        switch (theme){
            case "Dark":
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                break;
            case "Light":
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                break;
            case "Default":
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
                break;
        }
    }
    public void savaTheme(String theme){
        SharedPreferences sharedPreferences = this.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("theme",theme);
        editor.apply();
    }

    public String getSavedTheme(){
        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        return sharedPref.getString("theme", "Default");
    }

    public void setSavedTheme(){
        changeTheme(getSavedTheme());
    }
}