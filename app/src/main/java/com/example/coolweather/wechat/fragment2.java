package com.example.coolweather.wechat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.coolweather.DateActivity;
import com.example.coolweather.R;
import com.example.coolweather.WeatherActivity;
import com.example.coolweather.datedisplay;
import com.example.coolweather.util.HttpUtil;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Callback;
import okhttp3.Response;

public class fragment2 extends Fragment {
    private ImageView bingPicImg;
    public View onCreateView(LayoutInflater inflater , ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.wechat_fragment2, container, false);
        Button button1 = (Button) view.findViewById(R.id.button2);
        bingPicImg = (ImageView) view.findViewById(R.id.image);
        //Button add=(Button)view.findViewById(R.id.add);
        //Button look=(Button)view.findViewById(R.id.look);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String bingPic = prefs.getString("bing_pic", null);
        if (bingPic != null) {
            Glide.with(getActivity()).load(bingPic).into(bingPicImg);
        } else {
            loadBingPic();
        }
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
        /*add.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), DateActivity.class);
                startActivity(intent);
            }
        });
        look.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                getActivity().startActivity(new Intent(getActivity(), datedisplay.class));
            }
        });*/
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
