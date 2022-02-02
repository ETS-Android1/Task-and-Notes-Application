package com.example.newapplication.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newapplication.R;
import com.example.newapplication.Models.RecyclerModel;

import java.util.List;

/**
 * Adapter for Showing Interest(Channel) in Profile Activity
* */

public class AdapterRecycler extends RecyclerView.Adapter<AdapterRecycler.MyViewHolder> {

    List<RecyclerModel> lt;

    public AdapterRecycler(List<RecyclerModel> lt){
        this.lt = lt;
    }

    @NonNull
    @Override
    public AdapterRecycler.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.channels_row,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterRecycler.MyViewHolder holder, int position) {
        holder.name.setText(lt.get(position).getNameP());
        holder.imageView.setImageResource(lt.get(position).getImageP());
    }

    @Override
    public int getItemCount() {
        return lt.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        ImageView imageView;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.textChannel);
            imageView = (ImageView) itemView.findViewById(R.id.imageLogoChannel);
        }
    }
}
