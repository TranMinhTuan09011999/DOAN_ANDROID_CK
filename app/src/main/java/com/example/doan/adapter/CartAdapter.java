package com.example.doan.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doan.CartActivity;
import com.example.doan.MainActivity;
import com.example.doan.R;
import com.example.doan.model.Cart1;
import com.example.doan.model.DetailCart;
import com.example.doan.model.OrderDetails;
import com.example.doan.model.QuantityRequest;
import com.example.doan.model.SanPham1;
import com.example.doan.retrofit.APIUltils;
import com.example.doan.session.Session;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ItemHoler> {
    Context context;
    List<Cart1> list;
    Session session;
    String token;

    public CartAdapter(Context context, List<Cart1> list) {
        this.context = context;
        this.list = list;
    }


    @NonNull
    @Override
    public ItemHoler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cart,null);
        Animation ani = AnimationUtils.loadAnimation(context,R.anim.ani_itemlistview);
        view.setAnimation(ani);
        return new ItemHoler(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHoler holder, int position) {
        Cart1 cart1 = list.get(position);
        Picasso.with(context).load(cart1.getProduct().getImage()).into(holder.imgSP);
        holder.txtTenSP.setText(cart1.getProduct().getName());
        holder.txtSL.setText(String.valueOf(cart1.getQuantity()));
        NumberFormat defaultFormat = NumberFormat.getCurrencyInstance();
        Double gia = Double.valueOf(cart1.getPrice() - cart1.getPrice()*cart1.getProduct().getPromotion()/100);
        if(cart1.getProduct().getPromotion() > 0){
            holder.txtGiaSP.setText(defaultFormat.format(gia) + " $ " + "(-" + cart1.getProduct().getPromotion() + "%)");
        }else{
            holder.txtGiaSP.setText(defaultFormat.format(gia) + " $ ");
        }
        if (Integer.valueOf(holder.txtSL.getText().toString()) == 1){
            holder.btnCong.setVisibility(View.VISIBLE);
            holder.btnTru.setVisibility(View.INVISIBLE);
        }
        else{
            holder.btnCong.setVisibility(View.VISIBLE);
            holder.btnTru.setVisibility(View.VISIBLE);
        }

        holder.btnCong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                session = new Session(context);
                Long userId = session.getId();
                token = "Bearer " + session.getToken();
                Long quantityProduct = cart1.getProduct().getQuantity();
                if(quantityProduct == 0){

                }else{
                    Long quantityAfterAdding = cart1.getQuantity() + 1;
                    Long cartId = cart1.getId();
                    cart1.setQuantity(quantityAfterAdding);
                    QuantityRequest quantityRequest = new QuantityRequest(quantityAfterAdding);
                    holder.txtSL.setText(String.valueOf(cart1.getQuantity()));
                    Call<List<Cart1>> callback = APIUltils.getData().updateQuantityOfProductInCart(token,cartId,quantityRequest);
                    callback.enqueue(new Callback<List<Cart1>>() {
                        @Override
                        public void onResponse(Call<List<Cart1>> call, Response<List<Cart1>> response) {
                            list = response.body();
                            DecimalFormat format  = new DecimalFormat("###,###,###");
                            Double tongtien = 0.0;
                            for (Cart1 cart : list) {
                                tongtien += (cart.getPrice() - cart.getPrice()*cart.getProduct().getPromotion()/100)*cart.getQuantity();
                            }
                            MainActivity.tongtien = tongtien;
                            CartActivity.txtTT.setText("TỔNG TIỀN : " + format.format(tongtien) + " $");
                            Long quantity = quantityProduct - 1;
                            QuantityRequest quantityRequest1 = new QuantityRequest(quantity);
                            Call<SanPham1> callback1 = APIUltils.getData().updateQuantityOfProduct(cart1.getProduct().getId(), quantityRequest1);
                            callback1.enqueue(new Callback<SanPham1>() {
                                @Override
                                public void onResponse(Call<SanPham1> call, Response<SanPham1> response) {
                                    SanPham1 sp = response.body();
                                }
                                @Override
                                public void onFailure(Call<SanPham1> call, Throwable t) {

                                }
                            });
                            CartActivity.adapter.notifyDataSetChanged();
                            if (Integer.valueOf(holder.txtSL.getText().toString()) == 1){
                                holder.btnCong.setVisibility(View.VISIBLE);
                                holder.btnTru.setVisibility(View.INVISIBLE);
                            }
                            else{
                                holder.btnCong.setVisibility(View.VISIBLE);
                                holder.btnTru.setVisibility(View.VISIBLE);
                            }

                        }
                        @Override
                        public void onFailure(Call<List<Cart1>> call, Throwable t) {

                        }
                    });
                }
            }
        });

        holder.btnTru.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                session = new Session(context);
                Long userId = session.getId();
                token = "Bearer " + session.getToken();
                holder.txtSL.setText(String.valueOf(cart1.getQuantity()));
                Long cartId = cart1.getId();
                Long quantityAfterAdding = cart1.getQuantity() - 1;
                cart1.setQuantity(quantityAfterAdding);
                QuantityRequest quantityRequest = new QuantityRequest(quantityAfterAdding);
                Call<List<Cart1>> callback = APIUltils.getData().updateQuantityOfProductInCart(token,cartId,quantityRequest);
                callback.enqueue(new Callback<List<Cart1>>() {
                    @Override
                    public void onResponse(Call<List<Cart1>> call, Response<List<Cart1>> response) {
                        list = response.body();
                        DecimalFormat format  = new DecimalFormat("###,###,###");
                        Double tongtien = 0.0;
                        for (Cart1 cart : list) {
                            tongtien += (cart.getPrice() - cart.getPrice()*cart.getProduct().getPromotion()/100)*cart.getQuantity();
                        }
                        MainActivity.tongtien = tongtien;
                        CartActivity.txtTT.setText("TỔNG TIỀN : " + format.format(tongtien) + " $");
                        Long quantity = cart1.getProduct().getQuantity() + 1;
                        QuantityRequest quantityRequest1 = new QuantityRequest(quantity);
                        Call<SanPham1> callback1 = APIUltils.getData().updateQuantityOfProduct(cart1.getProduct().getId(), quantityRequest1);
                        callback1.enqueue(new Callback<SanPham1>() {
                            @Override
                            public void onResponse(Call<SanPham1> call, Response<SanPham1> response) {
                                SanPham1 sp = response.body();
                            }
                            @Override
                            public void onFailure(Call<SanPham1> call, Throwable t) {

                            }
                        });
                        if (Integer.valueOf(holder.txtSL.getText().toString()) == 1){
                            holder.btnCong.setVisibility(View.VISIBLE);
                            holder.btnTru.setVisibility(View.INVISIBLE);
                        }
                        else{
                            holder.btnCong.setVisibility(View.VISIBLE);
                            holder.btnTru.setVisibility(View.VISIBLE);
                        }

                    }
                    @Override
                    public void onFailure(Call<List<Cart1>> call, Throwable t) {

                    }
                });
            }
        });

        holder.cardView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(context);
                dialog.setTitle("XÁC NHẬN");
                dialog.setMessage("BẠN CÓ CHẮC CHẮN MUỐN XÓA SẢN PHẨM NÀY KHỎI GIỎ HÀNG KHÔNG");
                dialog.setPositiveButton("CÓ", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        session = new Session(context);
                        token = "Bearer " + session.getToken();
                        Long cartId = cart1.getId();
                        Call<List<Cart1>> callback = APIUltils.getData().deleteCart(token,cartId);
                        callback.enqueue(new Callback<List<Cart1>>() {
                            @Override
                            public void onResponse(Call<List<Cart1>> call, Response<List<Cart1>> response) {
                                list = response.body();
                                Double tongtien = 0.0;
                                for (Cart1 cart : list) {
                                    tongtien += (cart.getPrice() - cart.getPrice()*cart.getProduct().getPromotion()/100)*cart.getQuantity();
                                }
                                MainActivity.tongtien = tongtien;
                                CartActivity.txtTT.setText("TỔNG TIỀN : " + defaultFormat.format(MainActivity.tongtien) + " VNĐ");
                                CartActivity.adapter.notifyDataSetChanged();
                                Long a = cart1.getProduct().getQuantity();
                                Long b = cart1.getQuantity();
                                Long quantityProduct = cart1.getProduct().getQuantity() + cart1.getQuantity();
                                QuantityRequest quantityRequest = new QuantityRequest(quantityProduct);
                                Call<SanPham1> call1 = APIUltils.getData().updateQuantityOfProduct(cart1.getProduct().getId(), quantityRequest);
                                call1.enqueue(new Callback<SanPham1>() {
                                    @Override
                                    public void onResponse(Call<SanPham1> call, Response<SanPham1> response) {
                                        SanPham1 sp;
                                        sp = response.body();
                                    }
                                    @Override
                                    public void onFailure(Call<SanPham1> call, Throwable t) {

                                    }
                                });

                                if (list.size() == 0){
                                    CartActivity.txtTB.setVisibility(View.VISIBLE);
                                };
                            }
                            @Override
                            public void onFailure(Call<List<Cart1>> call, Throwable t) {

                            }
                        });

                    }
                });

                dialog.setNegativeButton("KHÔNG", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                dialog.show();
                return true;
            }
        });
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    class ItemHoler extends RecyclerView.ViewHolder{

        TextView txtTenSP,txtGiaSP,txtSL;
        Button btnCong,btnTru;
        ImageView imgSP;
        CardView cardView;

        public ItemHoler(@NonNull View itemView) {
            super(itemView);
            txtTenSP = itemView.findViewById(R.id.txtTenSP_Cart);
            txtGiaSP = itemView.findViewById(R.id.txtGiaSP_Cart);
            imgSP = itemView.findViewById(R.id.imgCart);
            txtSL = itemView.findViewById(R.id.txtSL_Cart);
            btnCong = itemView.findViewById(R.id.btnCong);
            btnTru = itemView.findViewById(R.id.btnTru);
            cardView = itemView.findViewById(R.id.cvItemCart);
        }
    }
}
