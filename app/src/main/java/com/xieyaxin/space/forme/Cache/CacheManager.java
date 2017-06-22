package com.xieyaxin.space.forme.Cache;

import android.content.Context;
import android.util.Log;

import com.xieyaxin.space.forme.Interface.JudgeListener;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Created by John on 2017/4/23.
 */

public class CacheManager {

    private static CacheManager instance=null;
    private static Context context;

    public static void init(Context mcontext){
        context=mcontext;
    }

    private CacheManager() {
    }

    public static CacheManager getInstance(){
        if (instance==null){
            instance=new CacheManager();
        }
        return instance;
    }

    public void addCache(CacheData cacheData, JudgeListener listener){
        if (cacheData==null) return;
        try {
            File file=new File(context.getCacheDir(),cacheData.getKey());
            Log.d("MainActivity", context.getCacheDir().getPath());
            if (!file.exists()){
                file.createNewFile();
            }
            ObjectOutputStream oos=new ObjectOutputStream(new FileOutputStream(file));
            oos.writeObject(cacheData);
            oos.close();
            listener.onSuccess();
        } catch (IOException e) {
            e.printStackTrace();
            listener.onError(e);
        }
    }

    public CacheData getCache(String key){
        try {
            File file=new File(context.getCacheDir(),key);
            if (file==null) return null;
            ObjectInputStream ois=new ObjectInputStream(new FileInputStream(file));
            CacheData cacheData= (CacheData) ois.readObject();
            ois.close();
            if (cacheData.isValid()){
                return cacheData;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
