package com.xieyaxin.space.forme;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.transition.Transition;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.signature.StringSignature;
import com.xieyaxin.space.forme.Bean.MeiZhi;
import com.xieyaxin.space.forme.Bean.Path;
import com.xieyaxin.space.forme.Interface.JudgeListener;
import com.xieyaxin.space.forme.Interface.ObjectListener;
import com.xieyaxin.space.forme.UI.ToolBarCreate;
import com.xieyaxin.space.forme.Util.GlideLoad;
import com.xieyaxin.space.forme.Util.SDFileHelper;
import com.xieyaxin.space.forme.Util.ScreenUtil;
import com.xieyaxin.space.forme.Util.ShowUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by John on 2017/4/22.
 */

public class ShowPicActivity extends AppCompatActivity {

    @BindView(R.id.img_show_meizhi)
    ImageView imgMeizhi;
    @BindView(R.id.tv_download)
    TextView tvDownload;
    @BindView(R.id.tv_upload)
    TextView tvUpload;
    @BindView(R.id.tv_opengallery)
    TextView tvOpengallery;

    private MeiZhi meiZhi;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_pic);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        new ToolBarCreate(this, "妹纸", true);
    }

    //EventBus-----------
    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void meiZhiEvent(MeiZhi meiZhi) {
        this.meiZhi = meiZhi;
        Glide.with(this)
                .load(Path.path)
                .signature(new StringSignature("1.0.0"))
//                .centerCrop()
                .override(Target.SIZE_ORIGINAL,Target.SIZE_ORIGINAL)
                .dontAnimate()
                .into(imgMeizhi);
//        GlideLoad.getInstance().getImageCache(this,imgMeizhi,meiZhi.getUrl());
    }

    //EventBus-----------


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @OnClick({R.id.tv_download, R.id.tv_upload, R.id.tv_opengallery})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_download:
                if (!new File(SDFileHelper.filepath+meiZhi.getCreatedAt() + ".jpg").exists()){
                    SDFileHelper.newInstance(this)
                            .savePicture(meiZhi.getCreatedAt() + ".jpg", meiZhi.getUrl(), new JudgeListener() {
                                @Override
                                public void onSuccess() {
                                    Snackbar.make(tvDownload, "已保存到相册", Snackbar.LENGTH_LONG)
                                            .setAction("打开相册", new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
                                                    PackageManager packageManager = getPackageManager();
                                                    Intent intent;
                                                    intent = packageManager.getLaunchIntentForPackage("com.android.gallery3d");
                                                    startActivity(intent);
                                                }
                                            }).show();
                                }

                                @Override
                                public void onError(Exception e) {
                                    ShowUtil.showToast(getApplicationContext(), e.getMessage());
                                }
                            });
                }else {
                    Snackbar.make(tvDownload, "已存在图片", Snackbar.LENGTH_LONG).show();
                }
                break;
            case R.id.tv_upload://"com.example.jhon.venue"
//                    PackageManager packageManager = getPackageManager();
//                    Intent intent = new Intent("com.jhon.venue");
//                    intent.putExtra("path",SDFileHelper.getFilename());
////                intent.setAction("com.example.jhon.venue.receiver");
////                intent = packageManager.getLaunchIntentForPackage("com.example.jhon.venue");
//                    List<ResolveInfo> resolveInfos=packageManager.queryIntentActivities(intent,PackageManager.MATCH_DEFAULT_ONLY);
//                    if (resolveInfos.size()>0){
//                        ShowUtil.showToast(getApplicationContext(),"有匹配的activity");
//                        SDFileHelper.setFilename("");
//                        startActivityForResult(intent,12);
//                    }else {
//                        ShowUtil.showToast(getApplicationContext(),"没有匹配的activity");
//                    }
//                if (intent!=null){
////                    sendBroadcast(intent);
//                    startActivity(intent);
//                }else {
//                    ShowUtil.showToast(getApplicationContext(),"你未安装Venue");
//                }
                if (!new File(SDFileHelper.filepath+meiZhi.getCreatedAt() + ".jpg").exists()){
                    SDFileHelper.newInstance(this)
                            .savePicture(meiZhi.getCreatedAt() + ".jpg", meiZhi.getUrl(), new JudgeListener() {
                                @Override
                                public void onSuccess() {
                                    PackageManager packageManager = getPackageManager();
                                    Intent intent = new Intent("com.jhon.venue");
                                    intent.putExtra("path", SDFileHelper.filepath+meiZhi.getCreatedAt() + ".jpg");
                                    List<ResolveInfo> resolveInfos = packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
                                    if (resolveInfos.size() > 0) {
                                        ShowUtil.showToast(getApplicationContext(), "有匹配的activity");
                                        startActivityForResult(intent, 12);
                                    } else {
                                        ShowUtil.showToast(getApplicationContext(), "没有匹配的activity");
                                    }
                                }

                                @Override
                                public void onError(Exception e) {
                                    ShowUtil.showToast(getApplicationContext(), e.getMessage());
                                }
                            });
                }else {
                    PackageManager packageManager = getPackageManager();
                    Intent intent = new Intent("com.jhon.venue");
                    intent.putExtra("path", SDFileHelper.filepath+meiZhi.getCreatedAt() + ".jpg");
                    List<ResolveInfo> resolveInfos = packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
                    if (resolveInfos.size() > 0) {
                        ShowUtil.showToast(getApplicationContext(), "有匹配的activity");
                        startActivityForResult(intent, 12);
                    } else {
                        ShowUtil.showToast(getApplicationContext(), "没有匹配的activity");
                    }
                }
                break;
            case R.id.tv_opengallery:
                PackageManager packageManager = getPackageManager();
                Intent intent;
                intent = packageManager.getLaunchIntentForPackage("com.android.gallery3d");
                startActivity(intent);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 12) {
            //当取消时，还是有返回码，但结果码为0，且data为空值
            if (data!=null){
                ShowUtil.showToast(getApplicationContext(), data.getStringExtra("callback"));
            }else {
                ShowUtil.showToast(getApplicationContext(),"已取消");
            }
        }
    }
}
