package com.example.firebasesearchtest03;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdapterClass extends RecyclerView.Adapter<AdapterClass.MyViewHolder> {
    ArrayList<Users> list;

    public AdapterClass(ArrayList<Users> list){
        this.list = list;
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        View mView;
        TextView name,status;
        public MyViewHolder(View itemVew){
            super(itemVew);
            mView = itemVew;
            name = mView.findViewById(R.id.tvName);
            status = mView.findViewById(R.id.tvDescription);

        }

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_holder, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.name.setText(list.get(position).getName());
        holder.status.setText(list.get(position).getStatus());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


}
