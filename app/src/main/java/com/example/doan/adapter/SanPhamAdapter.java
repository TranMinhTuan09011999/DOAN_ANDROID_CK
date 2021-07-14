package com.example.doan.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doan.CTSPActivity;
import com.example.doan.CartActivity;
import com.example.doan.R;
import com.example.doan.model.Cart1;
import com.example.doan.model.CartRequest;
import com.example.doan.model.ImageDetails;
import com.example.doan.model.SanPham1;
import com.example.doan.retrofit.APIUltils;
import com.example.doan.session.Session;
import com.ramotion.foldingcell.FoldingCell;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SanPhamAdapter extends RecyclerView.Adapter<SanPhamAdapter.ItemViewHolder> {

    Context context;
    List<SanPham1> listSP;
    Session session;
    String token;

    public SanPhamAdapter(Context context, List<SanPham1> listSP) {
        this.context = context;
        this.listSP = listSP;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_dt,null);
        Animation ani = AnimationUtils.loadAnimation(context,R.anim.ani_itemlistview);
        view.setAnimation(ani);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        SanPham1 sp = listSP.get(position);
        holder.txtTenSP_title.setText(sp.getName());
        holder.txtTenSP_content.setText(sp.getName());
        NumberFormat defaultFormat = NumberFormat.getCurrencyInstance();
        if(sp.getPromotion() > 0){ holder.txtGiaSP_title.setText("GIÁ : " + defaultFormat.format((sp.getPrice() - sp.getPrice()*sp.getPromotion()/100)) + "$" + " (-" + sp.getPromotion() + "%) " + "(SL:" + sp.getQuantity() + ")");
        }else {
            holder.txtGiaSP_title.setText("GIÁ : " + defaultFormat.format((sp.getPrice() - sp.getPrice()*sp.getPromotion()/100)) + "$" + " (SL:" + sp.getQuantity() + ")");
        }
        Picasso.with(context).load(sp.getImage()).into(holder.imgSP_title);
        //Picasso.with(context).load(sp.getHinhanh()).into(holder.imgSP_content);

        ArrayList<String> arr = new ArrayList<>();

        /*Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.1.4:8080/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        DataClient retrofitAPI = retrofit.create(DataClient.class);*/
        Call<List<ImageDetails>> callback = APIUltils.getData().getListImageByProductId(sp.getId());
        callback.enqueue(new Callback<List<ImageDetails>>() {
            @Override
            public void onResponse(Call<List<ImageDetails>> call, Response<List<ImageDetails>> response) {
                List<ImageDetails> list = response.body();
                for (ImageDetails item : list){
                    arr.add(item.getImage());
                }
                arr.add(sp.getImage());
                for (int  i = 0 ; i < arr.size(); i++){
                    ImageView img = new ImageView(context);
                    Picasso.with(context).load(arr.get(i)).into(img);
                    img.setScaleType(ImageView.ScaleType.FIT_XY);
                    holder.viewFlipper.addView(img);
                }
            }

            @Override
            public void onFailure(Call<List<ImageDetails>> call, Throwable t) {

            }
        });

        holder.viewFlipper.setFlipInterval(2500);
        holder.viewFlipper.setAutoStart(true);
        Animation animation_in = AnimationUtils.loadAnimation(context,R.anim.slide_in_right);
        Animation animation_out = AnimationUtils.loadAnimation(context,R.anim.slide_out_right);
        holder.viewFlipper.setInAnimation(animation_in);
        holder.viewFlipper.setOutAnimation(animation_out);

        holder.foldingCell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.foldingCell.toggle(false);
            }
        });

        holder.btnXCT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,CTSPActivity.class);
                intent.putExtra("SANPHAM", (Serializable) sp);
                context.startActivity(intent);
            }
        });

        holder.btnMH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, CartActivity.class);
                intent.putExtra("SANPHAM", (Parcelable) sp);
                session = new Session(context);
                Long userId = session.getId();
                token = "Bearer " + session.getToken();

                System.out.println(userId);
                System.out.println(token);

                int qty = 1;

                CartRequest cartRequest = new CartRequest(sp.getId(), userId, 1, sp.getPrice());

                /*Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("http://192.168.1.4:8080/api/")
                        // as we are sending data in json format so
                        // we have to add Gson converter factory
                        .addConverterFactory(GsonConverterFactory.create())
                        // at last we are building our retrofit builder.
                        .build();
                DataClient retrofitAPI = retrofit.create(DataClient.class);*/
                Call<List<Cart1>> callback = APIUltils.getData().addCartByUserId(token,cartRequest);
                callback.enqueue(new Callback<List<Cart1>>() {
                    @Override
                    public void onResponse(Call<List<Cart1>> call, Response<List<Cart1>> response) {
                        List<Cart1> list = new ArrayList<Cart1>();
                        list = response.body();
                        context.startActivity(new Intent(context, CartActivity.class));
                    }
                    @Override
                    public void onFailure(Call<List<Cart1>> call, Throwable t) {

                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return listSP.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder{

        FoldingCell foldingCell;
        ImageView imgSP_title;
        ViewFlipper viewFlipper;
        TextView txtTenSP_title,txtTenSP_content,txtGiaSP_title;
        Button btnXCT,btnMH;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            foldingCell = itemView.findViewById(R.id.folding_cell);
            imgSP_title = itemView.findViewById(R.id.imgSP_title);
            viewFlipper = itemView.findViewById(R.id.viewFlipperCTSP);
            txtTenSP_title = itemView.findViewById(R.id.txtTenSP_title);
            txtTenSP_content = itemView.findViewById(R.id.txtTenSP_content);
            txtGiaSP_title = itemView.findViewById(R.id.txtGiaSP_title);
            btnXCT = itemView.findViewById(R.id.btnCTDT);
            btnMH = itemView.findViewById(R.id.btnMuaHang);
        }
    }
}
