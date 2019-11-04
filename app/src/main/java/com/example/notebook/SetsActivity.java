package com.example.notebook;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.Toast;

import java.util.Set;

public class SetsActivity extends AppCompatActivity implements View.OnClickListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sets);

        Switch switchPassword = findViewById(R.id.sets_swich_password);
        SharedPreferences preferences = getSharedPreferences("prefer_data",MODE_PRIVATE);
        switchPassword.setChecked(preferences.getBoolean("passwordOpen",false));

        LinearLayout buttonPassword = findViewById(R.id.Sets_button_password);
        buttonPassword.setOnClickListener(this);

        ActionBar actionBar = getSupportActionBar();
        if(actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        switchPassword.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    SharedPreferences.Editor editor = getSharedPreferences("prefer_data",MODE_PRIVATE).edit();
                    editor.putBoolean("passwordOpen",true);
                    editor.apply();
                }else {
                    SharedPreferences.Editor editor = getSharedPreferences("prefer_data",MODE_PRIVATE).edit();
                    editor.putBoolean("passwordOpen",false);
                    editor.apply();
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.Sets_button_password:
                Intent intent = new Intent(SetsActivity.this,SetPassWordActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
