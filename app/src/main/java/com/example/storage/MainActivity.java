package com.example.storage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String darkTheme = "Dark";
    public static final String lightTheme = "Light";

    public EditText editText;
    public Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        editText = (EditText) findViewById(R.id.username);
        button = (Button) findViewById(R.id.btnSave);

        button.setOnClickListener(this);

        setSavedTheme();
        setUserName();
    }

    private void setUserName() {
        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        String username = sharedPref.getString("username", "");
        editText.setText(username);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btnSave){
            String name = editText.getText().toString();
            SharedPreferences sharedPreferences = this.getPreferences(Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("username",name);
            editor.apply();
            Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show();

        }
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