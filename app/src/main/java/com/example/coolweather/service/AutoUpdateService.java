package com.example.coolweather.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.os.SystemClock;
import android.preference.PreferenceManager;

import com.example.coolweather.gson.Weather;
import com.example.coolweather.util.HttpUtil;
import com.example.coolweather.util.Utility;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Callback;
import okhttp3.Response;

public class AutoUpdateService extends Service {
    public AutoUpdateService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
    @Override

    public int onStartCommand(Intent intent, int flags, int startId) { updateWeather();
        updateBingPic();
        AlarmManager manager = (AlarmManager) getSystemService(ALARM_SERVICE);
        int anHour = 8 * 60 * 60 * 1000; // 这是8小时的毫秒数
        long triggerAtTime = SystemClock.elapsedRealtime() + anHour;
        Intent i = new Intent(this, AutoUpdateService.class);
        PendingIntent pi = PendingIntent.getService(this, 0, i, 0);
        manager.cancel(pi);
        manager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerAtTime, pi);
        return super.onStartCommand(intent, flags, startId);
    }

    /**
     更新天气信息

     */
    private void updateWeather(){

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String weatherString = prefs.getString("weather", null);
        if (weatherString != null) {
            //有缓存时直接解析天气数据



            Weather weather = Utility.handleWeatherResponse(weatherString);
            String weatherId = weather.basic.weatherId;

            String weatherUrl = "https://free-api.heweather.net/s6/weather?location=" + weatherId + "&key=07e61d71227042f4b4445779a49e7d1d";

            HttpUtil.sendOkHttpRequest(weatherUrl, new Callback() { @Override

            public void onResponse(@NotNull okhttp3.Call call, @NotNull Response response) throws IOException {
                String responseText = response.body().string();
                Weather weather = Utility.handleWeatherResponse(responseText);
                if (weather != null && "ok".equals(weather.status)) {
                    SharedPreferences.Editor editor = PreferenceManager.
                            getDefaultSharedPreferences(AutoUpdateService.this).
                            edit();
                    editor.putString("weather", responseText);
                    editor.apply();

                }


            }

                @Override

                public void onFailure(@NotNull okhttp3.Call call, @NotNull IOException e) { e.printStackTrace();
                }
            });
        }

    }

    /**
     更新必应每日一图

     */
    private void updateBingPic() {

        String requestBingPic = "http://guolin.tech/api/bing_pic"; HttpUtil.sendOkHttpRequest(requestBingPic, new Callback() {
            @Override

            public void onResponse(@NotNull okhttp3.Call call, @NotNull Response response) throws IOException {
                String bingPic = response.body().string();
            SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(AutoUpdateService.this).edit();
            editor.putString("bing_pic", bingPic);
            editor.apply();

            }

            @Override

            public void onFailure(@NotNull okhttp3.Call call, @NotNull IOException e) {
                e.printStackTrace();
            }
        });

    }

}
