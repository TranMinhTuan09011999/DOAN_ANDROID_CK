package com.example.doan;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.doan.model.DetailCart;
import com.example.doan.model.LoginRequest;
import com.example.doan.model.User;
import com.example.doan.retrofit.APIUltils;
import com.example.doan.retrofit.DataClient;
import com.example.doan.session.Session;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONObject;

import java.io.Serializable;
import java.util.List;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {

    Button btnRegister,btnLogin;
    TextView txtLogin;
    TextInputEditText editEmail,editPassword;

    String email = "";
    String pass = "";
    public static int code = 0;
    public static User user = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.SEND_SMS,Manifest.permission.READ_SMS}, PackageManager.PERMISSION_GRANTED);
//        SmsManager manager = SmsManager.getDefault();
//        manager.sendTextMessage("0912438765",null,"content",null,null);
        setControll();
        setEvent();
    }

    public static void SendSMS(){
        Random random = new Random();
        code = random.nextInt(8999) + 1000;
        String content = "MÃ CODE : " + String.valueOf(code) ;
 //       content += "\nDANH SÁCH ĐƠN HÀNG";
//
//        for(DetailCart item : MainActivity.listCart){
//            content += "\nTên Mặt Hàng : " + item.getTen() + " - Số Lượng " + String.valueOf(item.getSoluong());
//        }
        SmsManager manager = SmsManager.getDefault();
        manager.sendTextMessage("0913576809",null,content,null,null);
    }

    private void setEvent() {
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,SignUpActivity.class);
                Pair[] pairs = new Pair[1];
                pairs[0] = new Pair<View,String>(txtLogin,"txtLogin");
                ActivityOptions activityOptions = ActivityOptions.makeSceneTransitionAnimation(LoginActivity.this,pairs);
                startActivity(intent,activityOptions.toBundle());
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!validate()){
                    return;
                }
                //DataClient data = APIUltils.getData();
                LoginRequest loginRequest = new LoginRequest(email,pass);

                /*Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("http://192.168.1.4:8080/api/")
                        // as we are sending data in json format so
                        // we have to add Gson converter factory
                        .addConverterFactory(GsonConverterFactory.create())
                        // at last we are building our retrofit builder.
                        .build();
                DataClient retrofitAPI = retrofit.create(DataClient.class);*/

                Call<User> callback = APIUltils.getData().login1(loginRequest);
                Session session = new Session(getApplicationContext());
                callback.enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        user = new User();
                        user = response.body();
                        if (user == null){
                            Toast.makeText(LoginActivity.this, "TÀI KHOẢN KHÔNG TỒN TẠI", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            if (user.getRoles()[0].compareTo("ROLE_USER") == 0){
                                Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                                intent.putExtra("USER", (Serializable) user);
                                session.setLogin(true);
                                session.setId(user.getId());
                                session.setToken(user.getToken());
                                startActivity(intent);
                            }
                            else{

                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        Toast.makeText(LoginActivity.this, "kkkk", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    public void setControll(){
        btnRegister = findViewById(R.id.imgbtnRegister);
        txtLogin = findViewById(R.id.txtLogin);
        editEmail = findViewById(R.id.editEmail_Login);
        editPassword = findViewById(R.id.editPassword_Login);
        btnLogin = findViewById(R.id.btnLogin);
    }

    public boolean validate(){
        email = editEmail.getText().toString();
        pass = editPassword.getText().toString();
        if (email.isEmpty()){
            editEmail.setError("Email không được trống");
            return false;
        }
        else{
            editEmail.setError(null);
        }
        if (pass.isEmpty()){
            editPassword.setError("Password không được trống");
            return false;
        }
        else{
            editPassword.setError(null);
        }
        return true;
    }
}