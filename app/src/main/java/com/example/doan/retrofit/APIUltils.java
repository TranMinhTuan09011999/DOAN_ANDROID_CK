package com.example.doan.retrofit;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIUltils {
    //public static final String base_url = "http://192.168.1.3:8080/doanwebservice/";
    public static DataClient getData(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.1.10:8080/api/")
                // as we are sending data in json format so
                // we have to add Gson converter factory
                .addConverterFactory(GsonConverterFactory.create())
                // at last we are building our retrofit builder.
                .build();
        return retrofit.create(DataClient.class);
    }
}
