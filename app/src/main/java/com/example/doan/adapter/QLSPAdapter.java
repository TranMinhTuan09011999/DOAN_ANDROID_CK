package com.example.doan.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doan.R;
import com.example.doan.model.SanPham1;
import com.squareup.picasso.Picasso;

import java.util.List;

public class QLSPAdapter extends RecyclerView.Adapter<QLSPAdapter.ItemViewHolder> {

    Context context;
    List<SanPham1> list;

    public QLSPAdapter(Context context, List<SanPham1> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_qlsp,null);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        SanPham1 sp = list.get(position);
        Picasso.with(context).load(sp.getImage()).into(holder.img);
        holder.txtTenSP.setText(sp.getName());

        holder.imgSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    class ItemViewHolder extends RecyclerView.ViewHolder{

        ImageView img,imgSua,imgCT;
        TextView txtTenSP;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            img     = itemView.findViewById(R.id.imgSP);
            imgSua  = itemView.findViewById(R.id.imgSua);
            txtTenSP= itemView.findViewById(R.id.txtTenQLSP);
        }
    }
}
