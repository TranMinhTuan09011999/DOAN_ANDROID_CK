package com.example.doan.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doan.R;
import com.example.doan.model.Order;
import com.example.doan.model.OrderDetails;
import com.example.doan.model.StatusRequest;
import com.example.doan.retrofit.APIUltils;
import com.example.doan.retrofit.DataClient;
import com.example.doan.session.Session;
import com.ramotion.foldingcell.FoldingCell;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LSMHAdapter extends RecyclerView.Adapter<LSMHAdapter.ItemHolder> {
    Context context;
    List<Order> list;
    Session session;
    String token;

    public LSMHAdapter(Context context, List<Order> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_lsmh,null);
        Animation ani = AnimationUtils.loadAnimation(context,R.anim.ani_itemlistview);
        view.setAnimation(ani);
        return new ItemHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder holder, int position) {
        Order order = list.get(position);
        Log.d("AAA",String.valueOf(order.getOrder_date()));
        holder.txtSTT.setText(position + 1 + "");
        holder.txtNM.setText("Ngày Mua : " + order.getOrder_date());
        Integer orderStatus = order.getStatus();
        Log.d("AAA",String.valueOf(orderStatus));
        if(orderStatus == 1)
        {
            holder.txtTThai.setText("Trạng Thái : " + "đang giao");
        }else if(orderStatus == 2){
            holder.txtTThai.setText("Trạng Thái : " + "đã giao");
        }else
        {
            holder.txtTThai.setText("Trạng Thái : " + "đã hủy");
        }

        NumberFormat defaultFormat = NumberFormat.getCurrencyInstance();
        holder.txtTT_title.setText("Tổng Tiền : " + defaultFormat.format(Double.valueOf(order.getAmount())) + " $");
        holder.txtTT_content.setText("Tổng Tiền : " + defaultFormat.format(Double.valueOf(order.getAmount()))+ " $");


        List<OrderDetails> cartList = new ArrayList<>();
        ITCartAdapter adapter = new ITCartAdapter(holder.foldingCell.getContext(),cartList);
        GridLayoutManager layout = new GridLayoutManager(holder.foldingCell.getContext(),1);
        //holder.recyclerView.setHasFixedSize(false);
        holder.recyclerView.setFocusable(true);
        layout.setOrientation(GridLayoutManager.HORIZONTAL);
        holder.recyclerView.setLayoutManager(layout);
        holder.recyclerView.setAdapter(adapter);

        session = new Session(context);
        Long userId = session.getId();
        token = "Bearer " + session.getToken();
        Log.d("A555",String.valueOf(order.getId()));
        Call<List<OrderDetails>> callback = APIUltils.getData().getOrderDetailsByOrderId(token,order.getId());
        callback.enqueue(new Callback<List<OrderDetails>>() {
            @Override
            public void onResponse(Call<List<OrderDetails>> call, Response<List<OrderDetails>> response) {
                List<OrderDetails> orderDetails;
                orderDetails = response.body();
                for (OrderDetails item : orderDetails){
                    cartList.add(new OrderDetails(item.getOrder(),item.getProduct(),item.getQuantity(), item.getAmount(), item.getDiscount()));
                }
                adapter.notifyDataSetChanged();
            }
            @Override
            public void onFailure(Call<List<OrderDetails>> call, Throwable t) {

            }
        });

        holder.foldingCell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.foldingCell.toggle(false);
            }
        });

        holder.txtSTT.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(context);
                dialog.setTitle("XÁC NHẬN");
                dialog.setMessage("BẠN CÓ CHẮC CHẮN MUỐN HỦY ĐƠN HÀNG NÀY KHÔNG ?");
                dialog.setPositiveButton("CÓ", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Long id = order.getId();
                        DataClient dataClient = APIUltils.getData();
                        StatusRequest statusRequest = new StatusRequest(3);
                        Call<Order> callback = APIUltils.getData().cancelOrder(token,order.getId(),statusRequest);
                        callback.enqueue(new Callback<Order>() {
                            @Override
                            public void onResponse(Call<Order> call, Response<Order> response) {
                                holder.txtTThai.setText("Trạng Thái : " + "đã hủy");
                                adapter.notifyDataSetChanged();
                            }

                            @Override
                            public void onFailure(Call<Order> call, Throwable t) {

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

    class ItemHolder extends RecyclerView.ViewHolder {

        FoldingCell foldingCell;
        TextView txtSTT,txtNM,txtTT_title,txtTT_content,txtTThai;
        RecyclerView recyclerView;

        public ItemHolder(@NonNull View itemView) {
            super(itemView);
            foldingCell = itemView.findViewById(R.id.fc);
            txtSTT = itemView.findViewById(R.id.txtSTT);
            txtNM = itemView.findViewById(R.id.txtNM_title);
            txtTT_title = itemView.findViewById(R.id.txtTT_LSMH);
            txtTT_content = itemView.findViewById(R.id.txtTT_Item_LSMH);
            recyclerView = itemView.findViewById(R.id.recyclerViewItemLSMH);
            txtTThai = itemView.findViewById(R.id.txtTThai);
        }
    }
}
