package com.xieyaxin.space.forme.Util;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

/**
 * Created by John on 2017/4/5.
 */

public class ShowUtil {

    public static void showToast(Context context,String title){
        Toast.makeText(context,title,Toast.LENGTH_SHORT).show();
    }

    public static void showSnack(View view, String title){
        Snackbar.make(view,title,Snackbar.LENGTH_SHORT).show();
    }

    public static void showLog(String tag,String content){
        Log.d(tag,content);
    }

    public static void showError(String tag,String content){
        Log.d(tag,content);
    }


}
