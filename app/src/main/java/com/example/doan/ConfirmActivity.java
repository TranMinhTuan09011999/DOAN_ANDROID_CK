package com.example.doan;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.doan.adapter.LSMHAdapter;
import com.example.doan.model.DetailCart;
import com.example.doan.model.Order;
import com.example.doan.model.OrderRequest;
import com.example.doan.retrofit.APIUltils;
import com.example.doan.retrofit.DataClient;
import com.example.doan.session.Session;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.InetAddress;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class ConfirmActivity extends AppCompatActivity {

    EditText recieverTxt,addressTxt,phoneTxt;
    TextView TongTienTxt;
    Button btnCkeckout;
    String content = null;
    Session session;
    String token;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.checkout);
        setControl();
        setEvent();
    }

    private void setControl() {
        recieverTxt = findViewById(R.id.recieverId);
        addressTxt = findViewById(R.id.addressId);
        phoneTxt = findViewById(R.id.phoneId);
        btnCkeckout = findViewById(R.id.btnCkeckout);
        TongTienTxt = findViewById(R.id.txtTongTien);
    }

    public boolean checkPermission(String per){
        //
        int check = ContextCompat.checkSelfPermission(this,per);
        return (check == PackageManager.PERMISSION_GRANTED);
    }

    private void setEvent() {
        NumberFormat defaultFormat = NumberFormat.getCurrencyInstance();
        TongTienTxt.setText("TỔNG TIỀN : " + defaultFormat.format(MainActivity.tongtien) + " $");
        btnCkeckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                session = new Session(getApplicationContext());
                Long userId = session.getId();
                token = "Bearer " + session.getToken();

                String date = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
                NumberFormat defaultFormat = NumberFormat.getCurrencyInstance();
                TongTienTxt.setText("TỔNG TIỀN : " + defaultFormat.format(MainActivity.tongtien) + " $");
                String reciever = recieverTxt.getText().toString();
                String address = addressTxt.getText().toString();
                String phone = phoneTxt.getText().toString();
                Order order = new Order(date, MainActivity.tongtien, reciever, address, phone, userId, 1);
                Call<Order> callback = APIUltils.getData().checkout(token,order);
                callback.enqueue(new Callback<Order>() {
                    @Override
                    public void onResponse(Call<Order> call, Response<Order> response) {
                        Order order = response.body();
                        Toast.makeText(ConfirmActivity.this, "CẢM ƠN BẠN ĐÃ MUA HÀNG", Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public void onFailure(Call<Order> call, Throwable t) {

                    }
                });
                startActivity(new Intent(ConfirmActivity.this,MainActivity.class));
            }
        });
    }

    public void insertCart() throws JSONException {
        Long idUser = LoginActivity.user.getId();
        long millis=System.currentTimeMillis();
        java.sql.Date date =new java.sql.Date(millis);
        String trangthai = "CHUA GIAO";
        JSONArray array = new JSONArray();
        for (DetailCart item : MainActivity.listCart){
            JSONObject object = new JSONObject();
            object.put("idSP",item.getIdSP());
            object.put("soluong",item.getSoluong());
            array.put(object);
        }

        DataClient data = APIUltils.getData();
        /*Call<String> callback = data.insertCart(idUser,String.valueOf(date),String.valueOf(MainActivity.tongtien),trangthai,array);
        callback.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                //Toast.makeText(CartActivity.this, "CẢM ƠN BẠN ĐÃ MUA HÀNG", Toast.LENGTH_SHORT).show();
                MainActivity.listCart.clear();
                //txtTB.setVisibility(View.VISIBLE);
                //adapter.notifyDataSetChanged();
                MainActivity.tongtien = 0;
                //txtTT.setText("TỔNG TIỀN : 0 VNĐ");
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                //Toast.makeText(CartActivity.this, String.valueOf(t), Toast.LENGTH_SHORT).show();
            }
        });*/
    }
}