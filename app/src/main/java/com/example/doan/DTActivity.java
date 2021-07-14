package com.example.doan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.doan.adapter.SPMNAdapter;
import com.example.doan.adapter.SanPhamAdapter;
import com.example.doan.model.Category;
import com.example.doan.model.SanPham1;
import com.example.doan.retrofit.APIUltils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DTActivity extends AppCompatActivity {
    Toolbar toolbar;
    RecyclerView recyclerView;
    int idloaisp = 0;
    String tenloaisp = "";
    List<SanPham1> listSP;
    SanPhamAdapter adapter;
    SPMNAdapter adapter1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_d_t);
        setControll();
        setEvent();
        ActionBar();
        getDSSP();
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

    public void ActionBar(){
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void setEvent() {
        Intent intent = getIntent();
        idloaisp = intent.getIntExtra("IDLOAISP",1);
        tenloaisp = intent.getStringExtra("TENLOAISP");
        toolbar.setTitle(tenloaisp);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    private void setControll() {
        toolbar = findViewById(R.id.toolbarDSSP);
        recyclerView = findViewById(R.id.recyclerViewDSSP);
        listSP = new ArrayList<>();
        adapter = new SanPhamAdapter(DTActivity.this,listSP);
        LinearLayoutManager layout = new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layout);
        recyclerView.setFocusable(false);
    }

    public void getDSSP(){
        /*Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.1.4:8080/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        DataClient retrofitAPI = retrofit.create(DataClient.class);
        Log.d("AAA",tenloaisp);*/
        Call<Category> callback = APIUltils.getData().getListProductByCategoryName(tenloaisp);
        callback.enqueue(new Callback<Category>() {
            @Override
            public void onResponse(Call<Category> call, Response<Category> response) {
                Category category = response.body();
                for (SanPham1 item : category.getProducts()){
                    listSP.add(new SanPham1(item.getId(),item.getName(),item.getPrice(),item.getPromotion(),item.getDescription(),item.getImage(),item.getDeletestatus(), item.getQuantity()));
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<Category> call, Throwable t) {
                Toast.makeText(DTActivity.this, "Fail roi", Toast.LENGTH_SHORT).show();
            }
        });
        Log.d("AAA","SIZE OUT :" + String.valueOf(listSP.size()));
    }
}