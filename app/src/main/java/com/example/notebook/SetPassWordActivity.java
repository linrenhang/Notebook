package com.example.notebook;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class SetPassWordActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView passwordNew;
    private TextView passwordRepeat;
    private TextView passwordTips;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_pass_word);

        passwordNew = findViewById(R.id.set_password_new);
        passwordRepeat = findViewById(R.id.set_password_repeat);
        passwordTips = findViewById(R.id.set_password_tips);
        Button button = findViewById(R.id.set_password_button);
        button.setOnClickListener(this);

        ActionBar actionBar = getSupportActionBar();
        if(actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.set_password_button:
                if(passwordNew.getText().length()!=4){
                    Toast.makeText(SetPassWordActivity.this,"请输入密码",Toast.LENGTH_SHORT).show();
                }else if(passwordRepeat.getText().length()!=4){
                    Toast.makeText(SetPassWordActivity.this,"请确认密码",Toast.LENGTH_SHORT).show();
                }else if(!passwordNew.getText().toString().equals(passwordRepeat.getText().toString())){
                    Toast.makeText(SetPassWordActivity.this,"两次输入密码不对应",Toast.LENGTH_SHORT).show();
                }else{
                    SharedPreferences.Editor editor = getSharedPreferences("prefer_data",MODE_PRIVATE).edit();
                    editor.putString("password",passwordNew.getText().toString());
                    if(passwordTips==null){
                        editor.putString("tips","未设置提示");
                    }
                    else{
                        editor.putString("tips",passwordTips.getText().toString());
                    }
                    editor.apply();
                    Toast.makeText(SetPassWordActivity.this,"保存成功",Toast.LENGTH_SHORT).show();
                    finish();
                }

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
