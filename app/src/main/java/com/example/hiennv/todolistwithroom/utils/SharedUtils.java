package com.example.hiennv.todolistwithroom.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedUtils {
    private SharedPreferences preferences;
    public SharedUtils(Context context){
        preferences = context.getSharedPreferences(Const.FILE_NAME,Context.MODE_PRIVATE);
    }

    public SharedPreferences.Editor getEditor(){
        SharedPreferences.Editor editor = preferences.edit();
        return editor;
    }

    public  void setBoolean(String key, boolean value){
        SharedPreferences.Editor editor = getEditor();
        editor.putBoolean(key,value);
        editor.apply();
    }

    public boolean getBoolean(String key, boolean defaultValue){
        return preferences.getBoolean(key,defaultValue);
    }

    public  void  setLong(String key, long value){
        SharedPreferences.Editor editor = getEditor();
        editor.putLong(key,value);
        editor.apply();
    }

    public long getLong(String key, long defaultValue){
        return preferences.getLong(key,defaultValue);
    }
}
