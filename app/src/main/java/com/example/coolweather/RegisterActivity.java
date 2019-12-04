package com.example.coolweather;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {
    private DBHelper helper;
    private EditText et1;
    private EditText et2;
    private EditText et3;
    private Button b;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_register);
        helper=new DBHelper(this,"USERS.db",null,1);
        et1=(EditText)findViewById(R.id.number);
        et2=(EditText)findViewById(R.id.password);
        et3=(EditText)findViewById(R.id.repassword);
        b=(Button)findViewById(R.id.register);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                helper.getWritableDatabase();
                String number=et1.getText().toString();
                String password=et2.getText().toString();
                String repassword=et3.getText().toString();
                if(!password.equals(repassword))
                    Toast.makeText(RegisterActivity.this, "密码确认失败，请重新输入！", Toast.LENGTH_LONG).show();
                else {
                    ContentValues values=new ContentValues();
                    values.put("number",number);
                    values.put("password",password);
                    DBHelper helper=new DBHelper(getApplicationContext(),"USERS.db",null,1);
                    helper.insert(values);
                    Toast.makeText(RegisterActivity. this, "插入成功！", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(RegisterActivity. this, LoginActivity.class);
                    startActivity(intent);
                }
            }
        });
    }
}
