package com.example.doan.session;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class Session {
    SharedPreferences prefs;
    SharedPreferences.Editor editor;

    public Session(Context cntx) {
        // TODO Auto-generated constructor stub
        prefs = cntx.getSharedPreferences("AppKey",0);
        editor = prefs.edit();
        editor.apply();
    }

    public void setLogin(boolean login){
        editor.putBoolean("KEY_LOGIN", login);
        editor.commit();
    }

    public boolean getLogin(){
        return prefs.getBoolean("KEY_LOGIN",false);
    }

    public void setId(Long id){
        editor.putLong("KEY_ID", id);
        editor.commit();
    }

    public Long getId(){
        return prefs.getLong("KEY_ID",0);
    }

    public void setToken(String token)
    {
        editor.putString("TOKEN", token);
        editor.commit();
    }

    public String getToken()
    {
        return prefs.getString("TOKEN", "");
    }
}
