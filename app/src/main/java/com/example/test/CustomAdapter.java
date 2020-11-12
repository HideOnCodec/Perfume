package com.example.test;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.CustomViewHolder> {

    private ArrayList<perfume> arrayList;
    private Context context;


    public CustomAdapter(ArrayList<perfume> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        CustomViewHolder holder = new CustomViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        // arraylist에 firebase 데이터(향수 이미지)를 가져와서 adapter에 전송
        Glide.with(holder.itemView)
                .load(arrayList.get(position).getProfile())
                .into(holder.pf_profile);
        holder.pf_name.setText(arrayList.get(position).getName());
        holder.pf_rating.setText(String.valueOf(arrayList.get(position).getRating()));
        holder.pf_brand.setText(arrayList.get(position).getBrand());
    }

    @Override
    public int getItemCount() {
        // 삼항 연산자
        return (arrayList != null ? arrayList.size() : 0);
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        ImageView pf_profile;
        TextView pf_brand;
        TextView pf_rating;
        TextView pf_name;

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView); // itemView 상속
            //상속 받은 itemView 를 통해 객체를 얻음
            this.pf_profile = itemView.findViewById(R.id.pf_profile);
            this.pf_name = itemView.findViewById(R.id.pf_name);
            this.pf_brand = itemView.findViewById(R.id.pf_brand);
            this.pf_rating = itemView.findViewById(R.id.pf_rating);
        }
    }
}