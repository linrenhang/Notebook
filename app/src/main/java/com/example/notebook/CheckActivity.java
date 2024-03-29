package com.example.notebook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class CheckActivity extends AppCompatActivity implements View.OnClickListener {

    private String password="0000";
    private String inputPassword="";
    private String tips="";
    private int number=0;
    private ImageView dot1;
    private ImageView dot2;
    private ImageView dot3;
    private ImageView dot4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences preferences = getSharedPreferences("prefer_data",MODE_PRIVATE);
        password = preferences.getString("password","0000");
        tips = preferences.getString("tips","未设置提示");
        if(!preferences.getBoolean("passwordOpen",false)){
            Intent intent = new Intent(CheckActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
        setContentView(R.layout.activity_check);
        Button button1 = findViewById(R.id.check_num_1);
        Button button2 = findViewById(R.id.check_num_2);
        Button button3 = findViewById(R.id.check_num_3);
        Button button4 = findViewById(R.id.check_num_4);
        Button button5 = findViewById(R.id.check_num_5);
        Button button6 = findViewById(R.id.check_num_6);
        Button button7 = findViewById(R.id.check_num_7);
        Button button8 = findViewById(R.id.check_num_8);
        Button button9 = findViewById(R.id.check_num_9);
        Button button0 = findViewById(R.id.check_num_0);
        Button buttonTips = findViewById(R.id.check_tips);
        Button buttonDelete = findViewById(R.id.check_delete);
        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
        button4.setOnClickListener(this);
        button5.setOnClickListener(this);
        button6.setOnClickListener(this);
        button7.setOnClickListener(this);
        button8.setOnClickListener(this);
        button9.setOnClickListener(this);
        button0.setOnClickListener(this);
        buttonTips.setOnClickListener(this);
        buttonDelete.setOnClickListener(this);
        dot1 = findViewById(R.id.check_dot_1);
        dot2 = findViewById(R.id.check_dot_2);
        dot3 = findViewById(R.id.check_dot_3);
        dot4 = findViewById(R.id.check_dot_4);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.check_num_0:
                numPress("0");
                break;
            case R.id.check_num_1:
                numPress("1");
                break;
            case R.id.check_num_2:
                numPress("2");
                break;
            case R.id.check_num_3:
                numPress("3");
                break;
            case R.id.check_num_4:
                numPress("4");
                break;
            case R.id.check_num_5:
                numPress("5");
                break;
            case R.id.check_num_6:
                numPress("6");
                break;
            case R.id.check_num_7:
                numPress("7");
                break;
            case R.id.check_num_8:
                numPress("8");
                break;
            case R.id.check_num_9:
                numPress("9");
                break;
            case R.id.check_tips:
                if(tips.length()>0){
                    Toast.makeText(CheckActivity.this, tips, Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(CheckActivity.this, "未设置提示", Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.check_delete:
                if(inputPassword.length()>0){
                    inputPassword = inputPassword.substring(0,inputPassword.length()-1);
                    number--;
                    setDot();
                }
            default:
                break;
        }
    }
    public void numPress(String input) {
        if (inputPassword.length() < 4) {
            inputPassword += input;
            number++;
            setDot();
        }
        if (inputPassword.length() == 4) {
            if (inputPassword.equals(password)) {
                Intent intent = new Intent(CheckActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(CheckActivity.this, "密码错误", Toast.LENGTH_SHORT).show();
                inputPassword="";
                number=0;
                setDot();
            }
        }
    }
    public void setDot(){
        if(number==0){
            dot1.setImageResource(R.drawable.pen_white);
            dot2.setImageResource(R.drawable.pen_white);
            dot3.setImageResource(R.drawable.pen_white);
            dot4.setImageResource(R.drawable.pen_white);
        }
        if(number==1){
            dot1.setImageResource(R.drawable.pen_green);
            dot2.setImageResource(R.drawable.pen_white);
            dot3.setImageResource(R.drawable.pen_white);
            dot4.setImageResource(R.drawable.pen_white);
        }
        if(number==2){
            dot1.setImageResource(R.drawable.pen_green);
            dot2.setImageResource(R.drawable.pen_green);
            dot3.setImageResource(R.drawable.pen_white);
            dot4.setImageResource(R.drawable.pen_white);
        }
        if(number==3){
            dot1.setImageResource(R.drawable.pen_green);
            dot2.setImageResource(R.drawable.pen_green);
            dot3.setImageResource(R.drawable.pen_green);
            dot4.setImageResource(R.drawable.pen_white);
        }
        if(number==4){
            dot1.setImageResource(R.drawable.pen_green);
            dot2.setImageResource(R.drawable.pen_green);
            dot3.setImageResource(R.drawable.pen_green);
            dot4.setImageResource(R.drawable.pen_green);
        }


    }
}
