package com.example.coolweather.wechat;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.coolweather.LoginActivity;
import com.example.coolweather.MyApplication;
import com.example.coolweather.R;
import com.example.coolweather.RegisterActivity;

public class fragment4 extends Fragment {


    public View onCreateView(final LayoutInflater inflater , final ViewGroup container, Bundle savedInstanceState){
        MyApplication application = (MyApplication) getActivity().getApplicationContext();
        if(application.getNumber()==0){
        final View[] view = {inflater.inflate(R.layout.login_main, container, false)};
        Button button = (Button) view[0].findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                getActivity().startActivity(new Intent(getActivity(), LoginActivity.class));
            }

        });
        Button button1 = (Button) view[0].findViewById(R.id.button1);
        button1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                getActivity().startActivity(new Intent(getActivity(), RegisterActivity.class));
            }
        });
        return view[0];
        }
        else{
            final View[] view = {inflater.inflate(R.layout.display, container, false)};
            application = (MyApplication) this.getActivity().getApplicationContext();
            TextView user = (TextView) view[0].findViewById(R.id.Name);
            user.setText(application.getUser());
            return view[0];
        }
    }
}
