package com.example.coolweather;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateActivity extends AppCompatActivity {
    private dateDBHelper helper;
    private EditText et1;
    private EditText et2;
    private Button b;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.date_activity_main);
        helper=new dateDBHelper(this,"USERSS.db",null,1);
        et1=(EditText)findViewById(R.id.name);
        et2=(EditText)findViewById(R.id.like);
        b=(Button)findViewById(R.id.add);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                helper.getWritableDatabase();
                String name=et1.getText().toString();
                String like=et2.getText().toString();
                ContentValues values=new ContentValues();
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");// HH:mm:ss//获取当前时间
                Date date = new Date(System.currentTimeMillis());
                values.put("name",name);
                values.put("hobby",like);
                values.put("date",simpleDateFormat.format(date));
                dateDBHelper helper=new dateDBHelper(getApplicationContext(),"USERSS.db",null,1);
                helper.insert(values);
                Intent intent=new Intent(DateActivity.this,datedisplay.class);
                startActivity(intent);
                finish();
                Toast.makeText(DateActivity.this, "保存成功！", Toast.LENGTH_LONG).show();
            }
        });
    }
}
