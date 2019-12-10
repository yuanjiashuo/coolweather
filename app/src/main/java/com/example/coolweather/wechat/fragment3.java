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
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.coolweather.R;
import com.example.coolweather.WeatherActivity;
import com.example.coolweather.util.ChooseAreaFragment;
import com.example.coolweather.util.HttpUtil;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Callback;
import okhttp3.Response;

public class fragment3 extends Fragment {
    private ImageView bingPicImg;
    public View onCreateView(LayoutInflater inflater , ViewGroup container, Bundle savedInstanceState){
        View view=inflater.inflate(R.layout.wechat_fragment3,container,false) ;
        Button button2 = (Button) view.findViewById(R.id.camera);
        Button button3 = (Button) view.findViewById(R.id.police);
        Button button4 = (Button) view.findViewById(R.id.doctor);
        Button button5 = (Button) view.findViewById(R.id.fireman);
        bingPicImg = (ImageView) view.findViewById(R.id.Image);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String bingPic = prefs.getString("bing_pic", null);
        if (bingPic != null) {
            Glide.with(getActivity()).load(bingPic).into(bingPicImg);
        } else {
            loadBingPic();
        }
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
    private void loadBingPic() {

        String requestBingPic = "http://guolin.tech/api/bing_pic"; HttpUtil.sendOkHttpRequest(requestBingPic, new Callback() {
            @Override
            public void onResponse(@NotNull okhttp3.Call call, @NotNull Response response) throws IOException {
                final String bingPic = response.body().string();
                SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(getActivity()).edit();
                editor.putString("bing_pic", bingPic);
                editor.apply();
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Glide.with(getActivity()).load(bingPic).into
                                (bingPicImg);
                    }
                });
            }
            @Override
            public void onFailure(@NotNull okhttp3.Call call, @NotNull IOException e) {
                e.printStackTrace();
            }
        });
    }
}

