package com.example.coolweather.wechat;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coolweather.R;

import java.util.ArrayList;
import java.util.List;

public class fragment2 extends Fragment {
    private List<contacts> fruitList = new ArrayList<>();
    public View onCreateView(LayoutInflater inflater , ViewGroup container, Bundle savedInstanceState){
        View view=inflater .inflate(R.layout .wechat_fragment2,container,false) ;
        initcontacts(); // 初始化水果数据
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        contactAdapter adapter = new contactAdapter(fruitList);
        recyclerView.setAdapter(adapter);
        return view;
    }
    private void initcontacts() {

        for (int i = 0; i < 5; i++) {
            contacts a = new contacts("A", R.drawable.a);
            fruitList.add(a);
            contacts b = new contacts("B", R.drawable.b);
            fruitList.add(b);
            contacts c = new contacts("C", R.drawable.c);
            fruitList.add(c);

        }
    }
}
