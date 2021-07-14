package com.example.doan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.example.doan.adapter.SelectionAdapter;
import com.example.doan.adapter.SPMNAdapter;
import com.example.doan.model.DetailCart;
import com.example.doan.model.SanPham1;
import com.example.doan.model.Selection;
import com.example.doan.retrofit.APIUltils;
import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    public static List<DetailCart> listCart = new ArrayList<>();
    public static double tongtien = 0;

    Toolbar toolbar;
    NavigationView  navigationView;
    ListView listView;
    ViewFlipper viewFlipper;
    RecyclerView recyclerView;
    DrawerLayout drawerLayout;
    ArrayList<Selection> list;
    SelectionAdapter adapter;
    TextView txtSPMN;
    List<SanPham1> listSP;
    SPMNAdapter adapterSP;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setControll();
        setEvent();
        ActionBar();
        ActionViewFlipper();
        ActionNavigationView();
        getProduct();
        ItemNaviClick();
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
            //overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
        }
        return true;
    }

    public void ItemNaviClick(){
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0 :{
                        break;
                    }
                    case 1 :{
                        Intent intent = new Intent(MainActivity.this,DTActivity.class);
                        intent.putExtra("IDLOAISP",1);
                        intent.putExtra("TENLOAISP","LAPTOP");
                        startActivity(intent);
                        break;
                    }
                    case 2 :{
                        Intent intent = new Intent(MainActivity.this,DTActivity.class);
                        intent.putExtra("IDLOAISP",2);
                        intent.putExtra("TENLOAISP", "SMARTPHONE");
                        startActivity(intent);
                        break;
                    }
                    case 3 :{
                        break;
                    }
                    case 4 :{
                        startActivity(new Intent(MainActivity.this,LSMHActivity.class));
                        break;
                    }
                    case 5:{
                        startActivity(new Intent(MainActivity.this,LoginActivity.class));
                        break;
                    }
                }
            }
        });
    }

    private void getProduct() {
        /*DataClient data = APIUltils.getData();
        retrofit2.Call<List<SanPham>> callback = data.getSanPham();*/

        /*Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.1.4:8080/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        DataClient retrofitAPI = retrofit.create(DataClient.class);*/
        Call<List<SanPham1>> callback = APIUltils.getData().getSanPhamDiscount();
        callback.enqueue(new Callback<List<SanPham1>>() {
            @Override
            public void onResponse(Call<List<SanPham1>> call, Response<List<SanPham1>> response) {
                List<SanPham1> list = response.body();
                listSP = new ArrayList<>();
                adapterSP = new SPMNAdapter(MainActivity.this,list);
                for (SanPham1 item : list){
                    listSP.add(new SanPham1(item.getId(),item.getName(),item.getPrice(),item.getPromotion(),item.getDescription(),item.getImage(),item.getDeletestatus(), item.getQuantity()));
                }
                recyclerView.setAdapter(adapterSP);
                adapterSP.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<SanPham1>> call, Throwable t) {

            }
        });
    }

    private void ActionNavigationView() {

    }

    private void ActionViewFlipper() {
        ArrayList<String> arr = new ArrayList<>();
        arr.add("https://laptopg7.vn/images/detailed/101/Asus-ROG-Strix-Hero-II--01-1574835010.jpg");
        arr.add("https://hdlaptop.com.vn/img/laptop-gaming.PNG");
        arr.add("https://cdn.vietnammoi.vn/171464242508312576/2020/6/12/laptop-choi-game-1-159195353528213330545.jpg");
        arr.add("https://ben.com.vn/tin-tuc/wp-content/uploads/2020/09/9.jpg");
        arr.add("https://genk.mediacdn.vn/2019/4/23/photo-3-15560365277921416953914.jpg");

        for (int  i = 0 ; i < arr.size(); i++){
            ImageView img = new ImageView(getApplicationContext());
            Picasso.with(getApplicationContext()).load(arr.get(i)).into(img);
            img.setScaleType(ImageView.ScaleType.FIT_XY);
            viewFlipper.addView(img);
        }

        viewFlipper.setFlipInterval(3000);
        viewFlipper.setAutoStart(true);
        Animation animation_in = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_in_right);
        Animation animation_out = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_out_right);
        viewFlipper.setInAnimation(animation_in);
        viewFlipper.setOutAnimation(animation_out);
    }

    private void ActionBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(android.R.drawable.ic_menu_sort_by_size);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
    }

    private void setEvent() {
        list = new ArrayList<>();
        list.add(new Selection(R.drawable.house,"TRANG CHỦ"));
        list.add(new Selection(R.drawable.laptop,"LAPTOP"));
        list.add(new Selection(R.drawable.webpage,"SMARTPHONE"));
        list.add(new Selection(R.drawable.contact,"LIÊN HỆ"));
        list.add(new Selection(R.drawable.trolley,"ĐƠN HÀNG"));
        list.add(new Selection(R.drawable.exit,"THOÁT"));
        adapter = new SelectionAdapter(list,getApplicationContext());
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();


    }

    private void setControll() {
        toolbar = findViewById(R.id.toolbarMain);
        navigationView = findViewById(R.id.navigation);
        listView = findViewById(R.id.listViewMain);
        viewFlipper = findViewById(R.id.viewFlipperMain);
        recyclerView = findViewById(R.id.recyclerViewMain);
        recyclerView.setHasFixedSize(true);
        GridLayoutManager layout = new GridLayoutManager(MainActivity.this,2);
        layout.setOrientation(GridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layout);
        drawerLayout = findViewById(R.id.drawerLayout);
        txtSPMN = findViewById(R.id.txtSPMN);
    }

}