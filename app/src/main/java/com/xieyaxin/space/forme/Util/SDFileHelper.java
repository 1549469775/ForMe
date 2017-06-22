package com.xieyaxin.space.forme.Util;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.xieyaxin.space.forme.Interface.JudgeListener;

import java.io.File;
import java.io.FileOutputStream;

/**
 * 通过Glide保存 图片到本地
 * Created by John on 2017/4/23.
 */

public class SDFileHelper {

    private Context context;

    public static String filepath="/storage/emulated/0/MeiZhi/";

    private SDFileHelper(Context context) {
        this.context = context;
    }

    public static SDFileHelper newInstance(Context context) {
        return new SDFileHelper(context);
    }

    public void savePicture(final String filename, String url, final JudgeListener listener){
        Glide.with(context).load(url).asBitmap().toBytes().into(new SimpleTarget<byte[]>() {
            @Override
            public void onResourceReady(byte[] resource, GlideAnimation<? super byte[]> glideAnimation) {
                try {
                    if (saveFileToSD(filename,resource)){
                        listener.onSuccess();
                    }else {
                        listener.onError(new Exception("没有权限"));
                    }
                } catch (Exception e) {
                    listener.onError(e);
                }
            }
        });
    }

    private boolean saveFileToSD(String filename,byte[] bytes) throws Exception{
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){//
            String filepath=Environment.getExternalStorageDirectory().getCanonicalPath()+"/MeiZhi";
            Log.d("xyx",filepath);
            File dir1=new File(filepath);
            if (!dir1.exists()){
                dir1.mkdirs();
            }
            filename = filepath+"/"+filename;
//            File dir2=new File(filename);
//            if (!dir2.exists()) {
//                Log.d("xyx","saveFileToSD");
//                dir2.createNewFile();
//                Log.d("xyx",dir2.getPath());
//            }
            FileOutputStream outputStream=new FileOutputStream(filename);
            outputStream.write(bytes);
            //提醒相册图片更新
            Intent intent=new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            Uri uri=Uri.fromFile(new File(filename));
            intent.setData(uri);
            context.sendBroadcast(intent);

            outputStream.close();
            return true;
        }else {
            return false;
        }
    }
}
