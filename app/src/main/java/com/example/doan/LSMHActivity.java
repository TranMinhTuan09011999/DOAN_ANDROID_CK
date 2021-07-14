package com.example.doan;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.doan.adapter.LSMHAdapter;
import com.example.doan.model.Order;
import com.example.doan.retrofit.APIUltils;
import com.example.doan.session.Session;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LSMHActivity extends AppCompatActivity {

    Toolbar toolbar;
    RecyclerView recyclerView;
    List<Order> listOrder = new ArrayList<>();
    //LSMHAdapter adapter;
    Session session;
    String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_l_s_m_h);
        setControl();
        setEvent();
        //getDSGH();
    }

    private void getDSGH() {
        session = new Session(getApplicationContext());
        Long userId = session.getId();
        token = "Bearer " + session.getToken();

        Call<List<Order>> callback = APIUltils.getData().getOrderByUserId(token,userId);
        callback.enqueue(new Callback<List<Order>>() {
            @Override
            public void onResponse(Call<List<Order>> call, Response<List<Order>> response) {
                //List<Order> listCart = new ArrayList<>();
                listOrder = response.body();
                LSMHAdapter adapter = new LSMHAdapter(LSMHActivity.this,listOrder);
                GridLayoutManager layout = new GridLayoutManager(LSMHActivity.this,1);
                recyclerView.setHasFixedSize(false);
                layout.setOrientation(GridLayoutManager.VERTICAL);
                recyclerView.setLayoutManager(layout);
                recyclerView.setAdapter(adapter);
            }
            @Override
            public void onFailure(Call<List<Order>> call, Throwable t) {

            }
        });
        //Log.d("AAA",String.valueOf(listOrder.size()));
    }

    private void setEvent() {
        getDSGH();
    }

    private void setControl() {
        toolbar = findViewById(R.id.toolbarLSMH);
        recyclerView = findViewById(R.id.recyclerViewLSMH);
    }
}