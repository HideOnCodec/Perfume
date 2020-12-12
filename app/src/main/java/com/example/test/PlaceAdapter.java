package com.example.test;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class PlaceAdapter extends RecyclerView.Adapter<PlaceAdapter.PlaceViewHolder>{

    private ArrayList<placeList> arrayList;
    private Context context;
    public PlaceAdapter(ArrayList<placeList> arrayList, Context context) {
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
        holder.place_name.setText(arrayList.get(position).getPlace_name());
        holder.place_address.setText(arrayList.get(position).getPlace_address());
    }

    @Override
    public int getItemCount() { return (arrayList != null ? arrayList.size() : 0); }

    public class PlaceViewHolder extends RecyclerView.ViewHolder {
        TextView place_name;
        TextView place_address;

        public PlaceViewHolder(@NonNull View itemView) {
            super(itemView); // itemView 상속
            this.place_name = itemView.findViewById(R.id.place_name);
            this.place_address=itemView.findViewById(R.id.place_address);
        }
    }
}


