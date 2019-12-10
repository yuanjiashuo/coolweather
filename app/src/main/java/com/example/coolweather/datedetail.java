package com.example.coolweather;


import androidx.appcompat.app.AppCompatActivity;

import android.app.AppComponentFactory;
import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

public class datedetail extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.date_detail);
        final EditText name1=(EditText)findViewById(R.id.name1);
        TextView date1=(TextView)findViewById(R.id.date1);
        final EditText like1=(EditText)findViewById(R.id.like1);
        Bundle bundle=getIntent().getExtras();
        final String uname = bundle.getString("uname");
        String udate=bundle.getString("udate");
        final String uhobby=bundle.getString("uhobby");
        final String id=bundle.getString("id");
        name1.setText(uname);
        date1.setText(udate);
        like1.setText(uhobby);
        Button amend=(Button)findViewById(R.id.amend);
        amend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContentValues values=new ContentValues();
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");// HH:mm:ss//获取当前时间
                Date date = new Date(System.currentTimeMillis());
                String name=name1.getText().toString().trim();
                String hobby=like1.getText().toString().trim();
                values.put("name",name);
                values.put("hobby",hobby);
                values.put("date",simpleDateFormat.format(date));
                dateDBHelper helper=new dateDBHelper(getApplicationContext(),"USERSS.db",null,1);
                helper.update(id,values);
                Intent intent=new Intent(datedetail.this,datedisplay.class);
                startActivity(intent);
                finish();
                Toast.makeText(datedetail.this, "修改成功！", Toast.LENGTH_LONG).show();
            }
        });
    }
}
