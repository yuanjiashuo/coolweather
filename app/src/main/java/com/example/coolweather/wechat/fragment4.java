package com.example.coolweather.wechat;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.coolweather.DateActivity;
import com.example.coolweather.LoginActivity;
import com.example.coolweather.MyApplication;
import com.example.coolweather.R;
import com.example.coolweather.RegisterActivity;
import com.example.coolweather.datedisplay;

public class fragment4 extends Fragment {
    public View onCreateView(final LayoutInflater inflater , final ViewGroup container, Bundle savedInstanceState){
        final MyApplication application = (MyApplication) getActivity().getApplicationContext();
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
            TextView user = (TextView) view[0].findViewById(R.id.Name);
            user.setText(application.getUser());
            Button down=(Button)view[0].findViewById(R.id.down);
            Button add=(Button)view[0].findViewById(R.id.add);
            Button look=(Button)view[0].findViewById(R.id.look);
            add.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    getActivity().startActivity(new Intent(getActivity(), DateActivity.class));
                }
            });
            look.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    getActivity().startActivity(new Intent(getActivity(), datedisplay.class));
                }
            });
            down.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    application.setNumber(0);
                    getActivity().startActivity(new Intent(getActivity(), MainActivity.class));
                    getActivity().finish();
                }
            });
            return view[0];
        }


    }
}
