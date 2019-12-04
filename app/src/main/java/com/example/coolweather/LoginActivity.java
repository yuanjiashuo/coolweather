package com.example.coolweather;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.coolweather.wechat.MainActivity;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

public class LoginActivity extends AppCompatActivity {
    private DBHelper helper;
    private EditText user, password;
    private String number;
    private String psd;
    public String username;
    public String mpassword;

    EventHandler eventHandler;
    private EditText edit_phone;
    private EditText edit_cord;
    private boolean coreflag = false;
    private Button bt_getphonecore;
    private Button bt_login;
    private String cord_number;
    private String phone_number;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_login);
        //数据库操作
        helper = new DBHelper(this, "USERS.db", null, 1);
        final SQLiteDatabase database = helper.getReadableDatabase();


        Cursor cursor = database.rawQuery("select * from DATA", null);
        if (cursor.moveToFirst()) {
            do {
                //从数据库获取到用户名、密码
                number = cursor.getString(cursor.getColumnIndex("number"));
                psd = cursor.getString(cursor.getColumnIndex("password"));
            } while (cursor.moveToNext());
            //关闭游标
            cursor.close();
        }
        initViews2();
        sms_verification();
    }
    /*public void initViews1() {
        user = findViewById(R.id.number);
        password = findViewById(R.id.password);
        Button Login = (Button) findViewById(R.id.bt_login);
        //登录按钮点击事件
        //验证当前账号、密码是否跟注册报讯在数据库的账号、密码一致，一致则允许登录
        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //获取输入框文本
                username = user.getText().toString().trim();
                mpassword = password.getText().toString().trim();
                //database.query("user",null,null,null,null,null,null);
                if (mpassword.equals(""))
                    Toast.makeText(LoginActivity.this, "密码不能为空", Toast.LENGTH_LONG).show();
            }
        });
    }*/
    public void sureuser(String username, String mpassword) {
        if (username.equals(number) && mpassword.equals(psd)) {
            //账号密码校验正确
            // 登陆成功，跳转的用户界面
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            MyApplication application = (MyApplication) this.getApplicationContext();
            application.setNumber(1);
            application.setUser(username);
            Log.d("user是",application.getUser());
            startActivity(intent);
            Toast.makeText(LoginActivity.this, "验证码输入正确,登陆成功", Toast.LENGTH_LONG).show();
            //提示
        } else {
            Toast.makeText(LoginActivity.this, "手机号/密码错误！", Toast.LENGTH_LONG).show();
        }
    }
    public void initViews2() {
        edit_phone = (EditText) findViewById(R.id.number); //你的手机号
        edit_cord = (EditText) findViewById(R.id.ed_code);//你的验证码
        bt_getphonecore = (Button) findViewById(R.id.bt_getphonecore);
        bt_login = (Button) findViewById(R.id.bt_login);
        password = findViewById(R.id.password);
        bt_getphonecore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (judPhone()) {//去掉左右空格获取字符串，是正确的手机号
                    SMSSDK.getVerificationCode("86", phone_number);//获取你的手机号的验证码
                    edit_cord.requestFocus();//判断是否获得焦点
                }
            }

        });
        bt_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (judCord()) {
                    SMSSDK.submitVerificationCode("86", phone_number, cord_number);//提交手机号和验证码13137041388
                }
            }
        });
    }

    private boolean judPhone() {//判断手机号是否正确
        //不正确的情况
        if (TextUtils.isEmpty(edit_phone.getText().toString().trim()))//对于字符串处理Android为我们提供了一个简单实用的TextUtils类，如果处理比较简单的内容不用去思考正则表达式不妨试试这个在android.text.TextUtils的类，主要的功能如下:
        //是否为空字符 boolean android.text.TextUtils.isEmpty(CharSequence str)
        {
            Toast.makeText(LoginActivity.this, "请输入您的电话号码", Toast.LENGTH_LONG).show();
            edit_phone.requestFocus();//设置是否获得焦点。若有requestFocus()被调用时，后者优先处理。注意在表单中想设置某一个如EditText获取焦点，光设置这个是不行的，需要将这个EditText前面的focusable都设置为false才行。
            return false;
        } else if (edit_phone.getText().toString().trim().length() != 11) {
            Toast.makeText(LoginActivity.this, "您的电话号码位数不正确", Toast.LENGTH_LONG).show();
            edit_phone.requestFocus();
            return false;
        }

        //正确的情况
        else {
            phone_number = edit_phone.getText().toString().trim();
            String num = "[1][3578]\\d{9}";
            if (phone_number.matches(num)) {
                return true;
            } else {
                Toast.makeText(LoginActivity.this, "请输入正确的手机号码", Toast.LENGTH_LONG).show();
                return false;
            }
        }
    }


    private boolean judCord() {//判断验证码是否正确
        judPhone();//先执行验证手机号码正确与否
        if (TextUtils.isEmpty(edit_cord.getText().toString().trim())) {//验证码
            Toast.makeText(LoginActivity.this, "请输入您的验证码", Toast.LENGTH_LONG).show();
            edit_cord.requestFocus();//聚集焦点
            return false;
        } else if (edit_cord.getText().toString().trim().length() != 4) {
            Toast.makeText(LoginActivity.this, "您的验证码位数不正确", Toast.LENGTH_LONG).show();
            edit_cord.requestFocus();
            return false;
        } else {
            cord_number = edit_cord.getText().toString().trim();
            return true;
        }
    }

    protected void onDestroy() {//销毁
        super.onDestroy();
        SMSSDK.unregisterEventHandler(eventHandler);

    }

    public void sms_verification() {
        //MobSDK.init(context, "28bc12fa236e4","44cb357655f252a8a75eac378b8283ad");
        eventHandler = new EventHandler() {
            public void afterEvent(int event, int result, Object data) {
                Message msg = new Message();//创建了一个对象
                msg.arg1 = event;
                msg.arg2 = result;
                msg.obj = data;
                handler.sendMessage(msg);
            }
        };
        SMSSDK.registerEventHandler(eventHandler);//注册短信回调（记得销毁，避免泄露内存）*/
    }

    /**
     * 使用Handler来分发Message对象到主线程中，处理事件
     */
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int event = msg.arg1;
            int result = msg.arg2;
            Object data = msg.obj;
            if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {//获取验证码成功
                if (result == SMSSDK.RESULT_COMPLETE) {
                    //回调完成
                    Toast.makeText(getApplicationContext(), "验证码获取成功", Toast.LENGTH_LONG).show();
                    edit_phone.requestFocus();//焦点
                    return;
                } else
                    Toast.makeText(getApplicationContext(), "验证码获取失败", Toast.LENGTH_LONG).show();
            }
            //回调完成
            if (result == SMSSDK.RESULT_COMPLETE) {
                if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {//提交验证码成功
                    mpassword = password.getText().toString().trim();
                    if (mpassword.equals(""))
                        Toast.makeText(LoginActivity.this, "密码不能为空", Toast.LENGTH_LONG).show();
                    else
                    sureuser(phone_number, mpassword);
                }
                else
                    Toast.makeText(getApplicationContext(), "验证码输入错误,登陆失败", Toast.LENGTH_LONG).show();
            }
        }
    };

}
