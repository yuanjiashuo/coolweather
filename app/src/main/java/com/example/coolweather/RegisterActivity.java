package com.example.coolweather;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
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
    private String num;
    private String psd;
    private Boolean c=true;
    private Boolean d=true;
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
                final SQLiteDatabase database = helper.getReadableDatabase();
                Cursor cursor = database.rawQuery("select * from DATA", null);
                if (cursor.moveToFirst()) {
                    do {
                        //从数据库获取到用户名、密码
                        num = cursor.getString(cursor.getColumnIndex("number"));
                        psd = cursor.getString(cursor.getColumnIndex("password"));
                        if(number.equals(num))
                            c=false;
                    } while (cursor.moveToNext());
                    //关闭游标
                    cursor.close();
                }
                if (TextUtils.isEmpty(et1.getText().toString().trim()))//对于字符串处理Android为我们提供了一个简单实用的TextUtils类，如果处理比较简单的内容不用去思考正则表达式不妨试试这个在android.text.TextUtils的类，主要的功能如下:
                //是否为空字符 boolean android.text.TextUtils.isEmpty(CharSequence str)
                {
                    Toast.makeText(RegisterActivity.this, "请输入您的电话号码", Toast.LENGTH_LONG).show();
                    d=false;
                    et1.requestFocus();//设置是否获得焦点。若有requestFocus()被调用时，后者优先处理。注意在表单中想设置某一个如EditText获取焦点，光设置这个是不行的，需要将这个EditText前面的focusable都设置为false才行。
                }
                else if (et1.getText().toString().trim().length() != 11) {
                    Toast.makeText(RegisterActivity.this, "您的电话号码位数不正确", Toast.LENGTH_LONG).show();
                    d=false;
                    et1.requestFocus();
                }
                //正确的情况
                else if (et1.getText().toString().trim().length() == 11){
                    String num = "[1][3578]\\d{9}";
                    if (!number.matches(num)) {
                        Toast.makeText(RegisterActivity.this, "请输入正确的手机号码", Toast.LENGTH_LONG).show();
                        d=false;
                    }
                }
                if(password.equals("")){
                    Toast.makeText(RegisterActivity.this, "请输入注册密码！", Toast.LENGTH_LONG).show();
                }
                else if(repassword.equals("")){
                    Toast.makeText(RegisterActivity.this, "请输入注册确认密码！", Toast.LENGTH_LONG).show();
                }
                else if(!password.equals(repassword))
                    Toast.makeText(RegisterActivity.this, "确认密码输入失败，请重新输入！", Toast.LENGTH_LONG).show();
                else if(c.equals(false)) {
                    c = true;
                    Toast.makeText(RegisterActivity.this, "手机号已注册！", Toast.LENGTH_LONG).show();
                }
                else if(d.equals(true)){
                    ContentValues values=new ContentValues();
                    values.put("number",number);
                    values.put("password",password);
                    DBHelper helper=new DBHelper(getApplicationContext(),"USERS.db",null,1);
                    helper.getWritableDatabase();
                    helper.insert(values);
                    Toast.makeText(RegisterActivity. this, "注册成功！", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(RegisterActivity. this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
                else
                    d=true;
            }
        });
    }
}
