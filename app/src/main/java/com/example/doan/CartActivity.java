package com.example.doan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.doan.adapter.CartAdapter;
import com.example.doan.model.Cart1;
import com.example.doan.model.SanPham1;
import com.example.doan.retrofit.APIUltils;
import com.example.doan.session.Session;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartActivity extends AppCompatActivity {

    public static CartAdapter adapter;
    Toolbar toolbar;
    RecyclerView recyclerView;
    public static TextView txtTT,txtTB;
    Button btnTT,btnMH;
    public static int idCart = 0;
    Session session;
    String token;
    List<Cart1> list = new ArrayList<>();
    //Double tongtien = 0.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        setControll();
        setEvent();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_cart,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.cart){
            startActivity(new Intent(getApplicationContext(),CartActivity.class));
        }
        return true;
    }

    private void setEvent() {
        //Lấy dữ liệu đối tượng đã gửi từ MainActivity (2 dòng code ở dưới)
        Intent intent = getIntent();
        SanPham1 sp = (SanPham1) intent.getSerializableExtra("SANPHAM");

        session = new Session(getApplicationContext());
        Long userId = session.getId();
        token = "Bearer " + session.getToken();

        Call<List<Cart1>> callback = APIUltils.getData().getCartByUser(token,userId);

        callback.enqueue(new Callback<List<Cart1>>() {
            @Override
            public void onResponse(Call<List<Cart1>> call, Response<List<Cart1>> response) {
                list = response.body();
                System.out.println(list.size());
                adapter = new CartAdapter(CartActivity.this,list);
                //Tạo layout scroll lên xuống, có 1 cột
                GridLayoutManager layout = new GridLayoutManager(CartActivity.this,1);
                //để cho list chạy mượt hơn
                recyclerView.setHasFixedSize(false);
                //set list view theo chiều dọc
                layout.setOrientation(GridLayoutManager.VERTICAL);
                recyclerView.setLayoutManager(layout);
                recyclerView.setAdapter(adapter);
                NumberFormat defaultFormat = NumberFormat.getCurrencyInstance();
                if (list.size() ==0){
                    txtTT.setText("TỔNG TIỀN : 0 $");
                    txtTB.setVisibility(View.VISIBLE);
                }
                else{

                    for (Cart1 cart : list) {
                        MainActivity.tongtien += (cart.getPrice() - cart.getPrice()*cart.getProduct().getPromotion()/100)*cart.getQuantity();
                    }
                    txtTT.setText("TỔNG TIỀN : " + defaultFormat.format(MainActivity.tongtien) + " $");
                    txtTB.setVisibility(View.INVISIBLE);
                }
            }
            @Override
            public void onFailure(Call<List<Cart1>> call, Throwable t) {

            }
        });

        btnTT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (list.size() == 0){
                    Toast.makeText(CartActivity.this, "Giỏ Hàng Đang Trống.Vui Lòng Chọn Sản Phẩm", Toast.LENGTH_SHORT).show();
                }
                else{
                    startActivity(new Intent(CartActivity.this,ConfirmActivity.class));
                    //startActivity(new Intent(CartActivity.this,ConfirmActivity.class));
                    overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
//                    try {
//
//                        insertCart();
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
                }
            }
        });

        btnMH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CartActivity.this,MainActivity.class));
                overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
            }
        });
    }

    private void setControll() {
        toolbar = findViewById(R.id.toolbarCart);
        recyclerView = findViewById(R.id.recyclerViewCart);
        txtTT = findViewById(R.id.txtTongTien);
        btnMH = findViewById(R.id.btnTTMH);
        btnTT = findViewById(R.id.btnThanhToan);
        txtTB = findViewById(R.id.txtTB);
    }
}