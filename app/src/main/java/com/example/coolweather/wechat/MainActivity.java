package com.example.coolweather.wechat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;


import com.example.coolweather.R;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private List<Fragment> list;
    private ViewPager myViewPager;
    private fragmentAdapter adapter;

    private TextView text_Control;
    private TextView text_Oversee;
    private TextView text_Over;
    private TextView text_connectDevice;
    private TextView topTitle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wechat_activity_main);
        initView();

    }

    private void initView() {
        text_Control = (TextView) findViewById(R.id.textView_control);
        text_Oversee = (TextView) findViewById(R.id.textView_oversee);
        text_Over = (TextView) findViewById(R.id.textView_over);
        text_connectDevice = (TextView) findViewById(R.id.textView_connectDevice);
        topTitle = (TextView) findViewById(R.id.textView_topTitle);

        myViewPager = (ViewPager) findViewById(R.id.myViewPager);
        //绑定点击事件
        myViewPager.addOnPageChangeListener(new MyPagerChangeListener());
        //把Fragment添加到List集合里面
        list = new ArrayList<>();
        list.add(new fragment1());
        list.add(new fragment2());
        list.add(new fragment3());
        list.add(new fragment4());
        adapter = new fragmentAdapter(getSupportFragmentManager(), list);
        myViewPager.setAdapter(adapter);
        myViewPager.setCurrentItem(0);  //初始化显示第一个页面

    }

    public class MyPagerChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrollStateChanged(int arg0) {
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }

        @Override
        public void onPageSelected(int arg0) {
            switch (arg0) {//状态改变时底部对应的字体颜色改变
                case 0:
                    text_Control.setTextColor(Color.GREEN);
                    text_Oversee.setTextColor(Color.BLACK);
                    text_Over.setTextColor(Color.BLACK);
                    text_connectDevice.setTextColor(Color.BLACK);
                    topTitle.setText("微信");
                    break;
                case 1:
                    text_Control.setTextColor(Color.BLACK);
                    text_Oversee.setTextColor(Color.GREEN);
                    text_Over.setTextColor(Color.BLACK);
                    text_connectDevice.setTextColor(Color.BLACK);
                    topTitle.setText("通讯录");
                    break;
                case 2:
                    text_Control.setTextColor(Color.BLACK);
                    text_Oversee.setTextColor(Color.BLACK);
                    text_Over.setTextColor(Color.GREEN);
                    text_connectDevice.setTextColor(Color.BLACK);
                    topTitle.setText("发现");
                    break;
                case 3:
                    text_Control.setTextColor(Color.BLACK);
                    text_Oversee.setTextColor(Color.BLACK);
                    text_Over.setTextColor(Color.BLACK);
                    text_connectDevice.setTextColor(Color.GREEN);
                    topTitle.setText("我的");
                    break;
            }

        }

    }

}
