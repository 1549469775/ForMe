package com.xieyaxin.space.forme.Bean;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by John on 2017/4/24.
 */

public class Preference {

    private static Preference preference = null;

    private Preference(){}

    public static Preference getInstance() {
        if (preference==null){
            synchronized (Preference.class){
                if (preference==null){
                    preference = new Preference();
                }
            }
        }
        return preference;
    }

    private SharedPreferences sharedPreferences;

    public void save(Context context, boolean isLoad){
        if (sharedPreferences==null){
            sharedPreferences=context.getSharedPreferences("data",Context.MODE_PRIVATE);
//            sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        }
        SharedPreferences.Editor edit=sharedPreferences.edit();
        edit.putBoolean("isLoad",isLoad);
        edit.apply();
    }

    public boolean getIsLoad(Context context){
        if (sharedPreferences==null){
            sharedPreferences=context.getSharedPreferences("data",Context.MODE_PRIVATE);
        }
        return sharedPreferences.getBoolean("isLoad",true);
    }
}
