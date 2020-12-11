package com.example.test;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class PlaceAdapter extends RecyclerView.Adapter<PlaceAdapter.PlaceViewHolder>{

    private ArrayList<String> arrayList;
    private Context context;

    public PlaceAdapter(ArrayList<String> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context=context;
    }


    @Override
    public PlaceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_place, parent, false);
        PlaceViewHolder holder = new PlaceViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull PlaceViewHolder holder, int position) {
        // arraylist에 firebase 데이터(향수 이미지)를 가져와서 adapter에 전송

        holder.place_name.setText(arrayList.get(position));

    }

    @Override
    public int getItemCount() {
        // 삼항 연산자
        return (arrayList != null ? arrayList.size() : 0);
    }

    public class PlaceViewHolder extends RecyclerView.ViewHolder {
        TextView place_name;

        public PlaceViewHolder(@NonNull View itemView) {
            super(itemView); // itemView 상속
            //상속 받은 itemView 를 통해 객체를 얻음

            this.place_name = itemView.findViewById(R.id.place_name);

        }

    }


}


