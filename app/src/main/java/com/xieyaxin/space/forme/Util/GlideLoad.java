package com.xieyaxin.space.forme.Util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory;
import com.bumptech.glide.load.resource.bitmap.GlideBitmapDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.signature.StringSignature;
import com.xieyaxin.space.forme.Adapter.MeiZhiAdapter;
import com.xieyaxin.space.forme.Bean.Path;
import com.xieyaxin.space.forme.Interface.JudgeListener;
import com.xieyaxin.space.forme.Interface.ObjectListener;
import com.xieyaxin.space.forme.Interface.SuccessListener;
import com.xieyaxin.space.forme.R;

import java.io.File;
import java.math.BigDecimal;
import java.util.concurrent.ExecutionException;

/**
 * Created by John on 2017/4/22.
 */

public class GlideLoad {

    private static GlideLoad glideLoad = null;

    private GlideLoad(){}

    public static GlideLoad getInstance() {
        if (glideLoad==null){
            synchronized (GlideLoad.class){
                if (glideLoad==null){
                    glideLoad = new GlideLoad();
                }
            }
        }
        return glideLoad;
    }
    private boolean loadImage=false;

    public void loadImageForItem(Context context, String url, final ImageView imageView){
        if (loadImage){
            loadImageFromNet(context,url,imageView);
        }else {
            Glide.with(context)
                    .load(R.mipmap.ic_launcher)
                    .centerCrop()
                    .signature(new StringSignature("1.0.0"))
                    .centerCrop()
                    .into(imageView);
        }
    }

    public void loadImagePlaceHolder(Context context,ImageView imageView){
            Glide.with(context)
                    .load(R.mipmap.ic_launcher)
                    .signature(new StringSignature("1.0.0"))
                    .into(imageView);
    }

    public void loadImageFromNet(Context context,String url,ImageView imageView){
            Glide.with(context)
                    .load(url)
                    .asBitmap()
                    .signature(new StringSignature("1.0.0"))
                    .placeholder(R.mipmap.ic_launcher)//Target.SIZE_ORIGINAL'
                    .centerCrop()
//                    .override(Target.SIZE_ORIGINAL,Target.SIZE_ORIGINAL)
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .centerCrop()
                    .dontAnimate()
                    .into(imageView);
    }

    public void loadImageFromNet(Context context, String url, final ImageView imageView, final SuccessListener listener){
        Glide.with(context)
                .load(url)
                .asBitmap()
                .signature(new StringSignature("1.0.0"))
                .placeholder(R.mipmap.ic_launcher)
                .centerCrop()
//                .override(ScreenUtil.getScreenWidth(context),ScreenUtil.getScreenHeight(context)/3)
//                .override(Target.SIZE_ORIGINAL,Target.SIZE_ORIGINAL)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .centerCrop()
                .dontAnimate()
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                        imageView.setImageBitmap(resource);
                        listener.onSuccess();
                    }
                });
    }

    public void setLoadImage(boolean loadImage) {
        this.loadImage = loadImage;
    }

    public boolean isLoadImage() {
        return loadImage;
    }

    public void getImageCache(Context context, String url, final SuccessListener listener){
        new GlidegetImageCacheAsyncTask(context, new SuccessListener() {
            @Override
            public void onSuccess() {
                listener.onSuccess();
            }
        }).execute(url);
    }

    /**
     * 异步获取缓存图像的类
     */
    public class GlidegetImageCacheAsyncTask extends AsyncTask<String ,Void ,File>{

        private Context context;
//        private ImageView imageView;
        private SuccessListener listener;

        public GlidegetImageCacheAsyncTask(Context context,SuccessListener listener) {
            this.context = context;
//            this.imageView = imageView;
            this.listener=listener;
        }

        @Override
        protected File doInBackground(String... params) {
            String imgUrl=params[0];
            try {
                return Glide.with(context)
                        .load(imgUrl)
                        .downloadOnly(Target.SIZE_ORIGINAL,Target.SIZE_ORIGINAL)
                        .get();
            } catch (Exception e) {
                Log.d("xyxasdadsad",e.getMessage());
                return null;
            }
        }

        @Override
        protected void onPostExecute(File file) {
            super.onPostExecute(file);
            if (file==null){
                return;
            }
            String path=file.getPath();
//            Bitmap bmp= BitmapFactory.decodeFile(path);
            Path.path=path;
            listener.onSuccess();
//                    Glide.with(context)
//                            .load(path)
//                            .asBitmap()
//                            .signature(new StringSignature("1.0.0"))
//                            .placeholder(R.mipmap.ic_launcher)
//                            .error(R.mipmap.ic_launcher)
//                            .override(Target.SIZE_ORIGINAL,Target.SIZE_ORIGINAL)
//                            .centerCrop()
//                            .dontAnimate()
//                            .into(imageView);
//            imageView.setImageBitmap(bmp);
        }
    }

    /**
     * 删除图片所有缓存
     * @param context
     *
     */
    public void clearImageAllCache(Context context){
        //需放在一个后台进程中
        clearImageDiskCache(context);
        clearImageMemoryCache(context);
//        String ImageExternallCacheDir=context.getCacheDir()+"/"+ InternalCacheDiskCacheFactory.DEFAULT_DISK_CACHE_DIR;
//        deleteFolderFile(ImageExternallCacheDir,true);
    }

    /**
     * 清除图片内存缓存
     * @param context
     */
    public void clearImageMemoryCache(Context context){
       try {
           if (Looper.myLooper()==Looper.getMainLooper()){//判断是否为主线程
               Glide.get(context).clearMemory();
           }
       }catch (Exception e){
           e.printStackTrace();
       }
    }

    /**
     * 清除图片磁盘缓存
     * @param context
     */
    public void clearImageDiskCache(final Context context){
        try {
            if (Looper.myLooper()==Looper.getMainLooper()){//判断是否为主线程
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Glide.get(context).clearDiskCache();
                    }
                }).start();
            }else {
                Glide.get(context).clearDiskCache();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 获取Glide造成的缓存大小
     * @param context
     * @return
     */
    public String getCacheSize(Context context){
        try {
            return getFormatSize(getFolderSize(new File(context.getCacheDir()+"/"+ InternalCacheDiskCacheFactory.DEFAULT_DISK_CACHE_DIR)));
        }catch (Exception e){
            e.printStackTrace();
        }
        return "";
    }


    /**
     * 获取指定文件夹内所有文件大小的总和
     * @param file
     * @return
     */
    public long getFolderSize(File file){
        long size=0;
        try{
            File[] fileList=file.listFiles();
            for (File aFile:fileList){
                if (aFile.isDirectory()){
                    size=size+getFolderSize(aFile);
                }else {
                    size=size+aFile.length();
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        Log.d("sdadsa",size+"");
        return size;
    }

    /**
     * 删除指定目录的文件，用于缓存删除
     * @param filepath
     * @param deleteThisPath
     */
    public void deleteFolderFile(String filepath,boolean deleteThisPath){
        if (!TextUtils.isEmpty(filepath)){
            try {
                File file=new File(filepath);
                if (file.isDirectory()){
                    File files[]=file.listFiles();
                    for (File file1:files){
                        deleteFolderFile(file1.getAbsolutePath(),true);
                    }
                }
                if (deleteThisPath){
                    if (!file.isDirectory()){
                        file.delete();
                    }else {
                        if (file.listFiles().length==0){
                            file.delete();
                        }
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    /**
     * 格式化单位
     * @param size  文件大小
     * @return
     */
    //格式化单位
    public String getFormatSize(double size){
        double kiloByte=size/1024;
        if (kiloByte<1){
            return size+"Byte";
        }

        double megaByte=kiloByte/1024;
        if (megaByte<1){
            BigDecimal result1 = new BigDecimal(Double.toString(kiloByte));
            return result1.setScale(2,BigDecimal.ROUND_HALF_UP).toPlainString()+"KB";
        }

        double gigaByte=megaByte/1024;
        if (gigaByte<1){
            BigDecimal result2 = new BigDecimal(Double.toString(megaByte));
            return result2.setScale(2,BigDecimal.ROUND_HALF_UP).toPlainString()+"MB";
        }

        double teraByte=gigaByte/1024;
        if (teraByte<1){
            BigDecimal result3 = new BigDecimal(Double.toString(gigaByte));
            return result3.setScale(2,BigDecimal.ROUND_HALF_UP).toPlainString()+"GB";
        }
        BigDecimal result4=new BigDecimal(teraByte);
        return result4.setScale(2,BigDecimal.ROUND_HALF_UP).toPlainString()+"TB";
    }

    public void clear(){
        glideLoad=null;
    }
}
