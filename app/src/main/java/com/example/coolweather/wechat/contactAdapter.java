package com.example.coolweather.wechat;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.coolweather.R;

import java.util.List;

public class contactAdapter extends RecyclerView.Adapter<contactAdapter.ViewHolder>{
    private List<contacts> mFruitList;

    static class ViewHolder extends RecyclerView.ViewHolder
    {
        View contactView;
        ImageView contactImage;
        TextView contactName;
        public ViewHolder(View view) {
            super(view);
            contactView = view;
            contactImage = (ImageView) view.findViewById(R.id.fruit_image);
            contactName = (TextView) view.findViewById(R.id.fruit_name);
        }
    }
    public contactAdapter(List<contacts> fruitList) {

        mFruitList = fruitList;
    }
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.wechat_contact_item, parent, false);
        final ViewHolder holder = new ViewHolder(view);
        holder.contactView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                contacts fruit = mFruitList.get(position); Toast.makeText(v.getContext(), "你点击了 " + fruit.getName(),
                        Toast.LENGTH_SHORT).show();
            }
        });

        holder.contactImage.setOnClickListener(new View.OnClickListener() { @Override
        public void onClick(View v) {
            int position = holder.getAdapterPosition();
            contacts fruit = mFruitList.get(position); Toast.makeText(v.getContext(), "你点击了 " + fruit.getName(),
                    Toast.LENGTH_SHORT).show();
        }
        });

        return holder;

    }

    @Override

    public void onBindViewHolder(ViewHolder holder, int position) {
        contacts fruit = mFruitList.get(position);
        holder.contactImage.setImageResource(fruit.getImageId());
        holder.contactName.setText(fruit.getName());

    }

    @Override

    public int getItemCount() {
        return mFruitList.size();

    }

}
