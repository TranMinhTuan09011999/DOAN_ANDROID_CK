package com.example.doan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.doan.model.MessageRespone;
import com.example.doan.model.SingupRequest;
import com.example.doan.retrofit.APIUltils;
import com.google.android.material.textfield.TextInputEditText;

import java.util.HashSet;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends AppCompatActivity {

    RelativeLayout layout;
    Animation animation;
    Toolbar toolbar;
    Button btnSignup;
    TextInputEditText editUsername,editEmail,editPassword,editPhone,editAddress, editFirstname, editLastname;

    String username;
    String email;
    String pass ;
    String phone ;
    String address ;
    String firstname;
    String lastname;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        setControll();
        setEvent();
    }


    private void setControll() {
        layout = findViewById(R.id.formSignup);
        toolbar = findViewById(R.id.toolbarheader);
        btnSignup = findViewById(R.id.btnCkeckout);
        editEmail = findViewById(R.id.editEmail_Signup);
        editAddress = findViewById(R.id.editAddress);
        editPassword = findViewById(R.id.editPassword_Signup);
        editPhone = findViewById(R.id.editPhone);
        editUsername = findViewById(R.id.editUsername);
        editFirstname = findViewById(R.id.editFirstname);
        editLastname = findViewById(R.id.editLastname);
    }

    private void setEvent() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        animation = AnimationUtils.loadAnimation(this,R.anim.uptodown);
        layout.setAnimation(animation);

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!validate()){
                    return;
                }
                else{
                    Set<String> roles = new HashSet<String>();
                    roles.add("user");
                    SingupRequest singupRequest = new SingupRequest(username,email,pass,phone,address,firstname,lastname,roles);

                    /*Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl("http://192.168.1.4:8080/api/")
                            // as we are sending data in json format so
                            // we have to add Gson converter factory
                            .addConverterFactory(GsonConverterFactory.create())
                            // at last we are building our retrofit builder.
                            .build();
                    DataClient retrofitAPI = retrofit.create(DataClient.class);*/

                    Call<MessageRespone> callback = APIUltils.getData().signup(singupRequest);

                    callback.enqueue(new Callback<MessageRespone>() {
                        @Override
                        public void onResponse(Call<MessageRespone> call, Response<MessageRespone> response) {
                            MessageRespone result = response.body();
                            if (result.getMessage().compareTo("User registered successfully!") ==0){
                                Toast.makeText(SignUpActivity.this, "ĐĂNG KÍ THÀNH CÔNG", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                            else{
                                Toast.makeText(SignUpActivity.this, "ĐĂNG KÍ THẤT BẠI", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<MessageRespone> call, Throwable t) {
                            Toast.makeText(SignUpActivity.this, "Lỗi", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home : {
                onBackPressed();
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    public boolean validate(){
        boolean check = true;
         username = editUsername.getText().toString();
         email = editEmail.getText().toString();
         pass = editPassword.getText().toString();
         phone = editPhone.getText().toString();
         address = editAddress.getText().toString();
        firstname = editFirstname.getText().toString();
        lastname = editLastname.getText().toString();

        if (username.isEmpty()){
            editUsername.setError("VUI LÒNG NHẬP Username");
            check = false;
        }
        else{
            editUsername.setError(null);
            check = true;
        }

        if (firstname.isEmpty()){
            editFirstname.setError("VUI LÒNG NHẬP Firstname");
            check = false;
        }
        else{
            editFirstname.setError(null);
            check = true;
        }

        if (lastname.isEmpty()){
            editLastname.setError("VUI LÒNG NHẬP Lastname");
            check = false;
        }
        else{
            editLastname.setError(null);
            check = true;
        }

        if (email.isEmpty()){
            editEmail.setError("VUI LÒNG NHẬP EMAIL");
            check = false;
        }
        else{
            String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
            if (!email.matches(emailPattern)){
                editEmail.setError("EMAIL KHÔNG HỢP LỆ");
                check = false;
            }
            else{
                editEmail.setError(null);
                check = true;
            }
        }

        if (pass.length() < 6){
            editPassword.setError("MẬT KHẨU PHẢI ĐỦ 6 KÍ TỰ");
            check =  false;
        }
        else{
            editPassword.setError(null);
            check = true;
        }

        if (phone.length() < 10){
            editPhone.setError("SDT KHÔNG HỢP LỆ");
            check =  false;
        }
        else{
            editPhone.setError(null);
            check = true;
        }

        if (address.isEmpty()){
            editAddress.setError("VUI LÒNG NHẬP ĐỊA CHỈ");
            check = false;
        }
        else{
            editAddress.setError(null);
            check = true;
        }

        return check;

    }
}