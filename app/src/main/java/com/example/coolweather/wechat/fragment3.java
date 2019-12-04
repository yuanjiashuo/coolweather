package com.example.coolweather.wechat;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import com.example.coolweather.R;
import com.example.coolweather.WeatherActivity;
import com.example.coolweather.util.ChooseAreaFragment;

public class fragment3 extends Fragment {
    public View onCreateView(LayoutInflater inflater , ViewGroup container, Bundle savedInstanceState){
        View view=inflater.inflate(R.layout.wechat_fragment3,container,false) ;
        Button button1 = (Button) view.findViewById(R.id.weather);
        Button button2 = (Button) view.findViewById(R.id.camera);
        Button button3 = (Button) view.findViewById(R.id.police);
        Button button4 = (Button) view.findViewById(R.id.doctor);
        Button button5 = (Button) view.findViewById(R.id.fireman);
        button1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
                if (prefs.getString("weather", null) != null) {
                    Intent intent = new Intent(getActivity(), WeatherActivity.class);
                    startActivity(intent);
                }else {
                    Intent intent = new Intent(getActivity(), weatherMainActivity.class);
                    startActivity(intent);
                }
            }

        });
        button2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent=new Intent( MediaStore.ACTION_IMAGE_CAPTURE);
                startActivity(intent);
            }

        });
        button3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent Intent =  new Intent(android.content.Intent.ACTION_DIAL,Uri.parse("tel:" + "110"));//跳转到拨号界面，同时传递电话号码
                startActivity(Intent);
            }

        });
        button4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent Intent =  new Intent(android.content.Intent.ACTION_DIAL, Uri.parse("tel:" + "120"));//直接拨打电话
                startActivity(Intent);
            }

        });
        button5.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent Intent =  new Intent(android.content.Intent.ACTION_DIAL, Uri.parse("tel:" + "119"));//直接拨打电话
                startActivity(Intent);
            }

        });
            return view;
    }
}

