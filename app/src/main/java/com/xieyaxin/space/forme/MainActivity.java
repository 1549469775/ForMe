package com.xieyaxin.space.forme;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.xieyaxin.space.forme.Adapter.MeiZhiAdapter;
import com.xieyaxin.space.forme.Bean.MeiZhi;
import com.xieyaxin.space.forme.Bean.Preference;
import com.xieyaxin.space.forme.Cache.CacheData;
import com.xieyaxin.space.forme.Cache.CacheManager;
import com.xieyaxin.space.forme.Http.HttpGet;
import com.xieyaxin.space.forme.Interface.JudgeListener;
import com.xieyaxin.space.forme.Interface.ResultListener;
import com.xieyaxin.space.forme.Interface.SuccessListener;
import com.xieyaxin.space.forme.Preference.MainAction;
import com.xieyaxin.space.forme.UI.ToolBarCreate;
import com.xieyaxin.space.forme.UI.TwoToExit;
import com.xieyaxin.space.forme.Util.GlideLoad;
import com.xieyaxin.space.forme.Util.RelpaceUtil;
import com.xieyaxin.space.forme.Util.ShowUtil;
import com.xieyaxin.space.forme.Util.TransitionHelper;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/*
* 该app包含以下几种库
* 1.eventbus用于传递数据，解耦控件
* 2.butterknife用于快速创建布局中的控件实例
* 3.glide图片处理的库子
* 4.okhttp网络请求的库字
* 5.okhttputils上面一个的简化操作
* 6.Gson json的解析包
* */
public class MainActivity extends AppCompatActivity {

    @BindView(R.id.rl_meizhi)
    RecyclerView rlMeizhi;
    @BindView(R.id.srl_meizhi)
    SwipeRefreshLayout srlMeizhi;

    private MeiZhiAdapter adapter;

    private List<MeiZhi> meiZhi;
    private List<MeiZhi> refreshMeiZhi;
    private List<MeiZhi> loadMoreMeiZhi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        new ToolBarCreate(this,"GanHuoIO");
//        CacheManager.init(this);
        initRV();
        initSRL();
        initData();
    }

    /**
     * 初始化获得的数据
     */
    private void initData(){
//        if (CacheManager.getInstance().getCache("meizhi")!=null){
//            CacheData cacheData=  CacheManager.getInstance().getCache("meizhi");
//            meiZhi.clear();
//            meiZhi.addAll((List<MeiZhi>)cacheData.getData());
//            if (meiZhi==null){
//                Log.d("MainActivity", "null");
//            }else {
//                Log.d("MainActivity", meiZhi.size()+"");
//                adapter.notifyDataSetChanged();
//            }
//        }
//        if (meiZhi.isEmpty()||meiZhi==null){
//            refreshData();
//        }else {
//
//        }
        srlMeizhi.setRefreshing(true);
        refreshData();
    }

    private Handler handler=new Handler();//用于模拟延迟

    /**
     * 初始化recyclerview的配置，包括点击事件，上拉加载
     */
    private void initRV(){
        if (meiZhi==null){
            meiZhi=new ArrayList<>();
        }
        rlMeizhi.setLayoutManager(new LinearLayoutManager(this));
        adapter=new MeiZhiAdapter(this,meiZhi,rlMeizhi);
        rlMeizhi.setAdapter(adapter);
        adapter.setOnClickListener(new MeiZhiAdapter.OnClickListener() {
            @Override
            public void onClick(final View view, final int position) {
                    GlideLoad.getInstance().getImageCache(MainActivity.this, meiZhi.get(position).getUrl(), new SuccessListener() {
                        @Override
                        public void onSuccess() {
                            EventBus.getDefault().postSticky(meiZhi.get(position));
                            Pair<View, String>[] pairs = TransitionHelper.createSafeTransitionParticipants(MainActivity.this, true,
                                    new Pair(view.findViewById(R.id.img_meizhi),getResources().getString(R.string.picTranslationName)));
                            ActivityOptionsCompat transitionActivityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(MainActivity.this, pairs);
                            startActivity(new Intent(MainActivity.this, ShowPicActivity.class), transitionActivityOptions.toBundle());
                        }
                    });
            }
        });
        //加载更多回调监听
        adapter.setOnMoreDataLoadListener(new MeiZhiAdapter.LoadMoreDataListener() {
            @Override
            public void loadMoreData() {
                //加入null值此时adapter会判断item的type
                meiZhi.add(null);
                adapter.notifyDataSetChanged();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        loadData();
                    }
                }, 500);
            }
        });
    }

    /**
     * swipe下拉刷新控件的配置
     */
    private void initSRL(){
        srlMeizhi.setColorSchemeColors(Color.RED,Color.GREEN,Color.YELLOW);
        srlMeizhi.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshData();
            }
        });
    }

    /**
     * 刷新数据，延迟以提高体验度
     */
    private void refreshData(){
        if (refreshMeiZhi==null){
            refreshMeiZhi=new ArrayList<>();
        }
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                MainAction.getInstance(MainActivity.this).getDataFromNet(new ResultListener() {
                    @Override
                    public void error(Exception e) {
                        srlMeizhi.setRefreshing(false);
                        ShowUtil.showToast(getApplicationContext(),e.getMessage());
                    }

                    @Override
                    public void result(Object object) {
                        srlMeizhi.setRefreshing(false);
                        refreshMeiZhi.clear();
                        refreshMeiZhi = (List<MeiZhi>) object;
                        RelpaceUtil.combine(refreshMeiZhi,meiZhi);
                        if (refreshMeiZhi.isEmpty()){
                            ShowUtil.showToast(getApplicationContext(),"童鞋，没东西给你");
                        }else {
                            adapter.notifyDataSetChanged();
                        }
                    }
                });
            }
        }, 500);
    }

    int page=2;
    private void loadData(){
        if (loadMoreMeiZhi==null){
            loadMoreMeiZhi=new ArrayList<>();
        }
        MainAction.getInstance(MainActivity.this).getMoreDataFromNet(page++, new ResultListener() {
            @Override
            public void error(Exception e) {
                meiZhi.remove(meiZhi.size() - 1);
                adapter.setLoaded(false);
                adapter.notifyDataSetChanged();
                page--;
                ShowUtil.showToast(getApplicationContext(),e.getMessage());
            }

            @Override
            public void result(Object object) {
                meiZhi.remove(meiZhi.size() - 1);
                loadMoreMeiZhi.clear();
                loadMoreMeiZhi = (List<MeiZhi>) object;
                meiZhi.addAll(loadMoreMeiZhi);
                adapter.notifyDataSetChanged();
                adapter.setLoaded(false);
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        CacheManager.getInstance()
//                .addCache(new CacheData("meizhi", meiZhi), new JudgeListener() {
//                    @Override
//                    public void onSuccess() {
//                        ShowUtil.showToast(getApplicationContext(),"meizhi");
//                    }
//
//                    @Override
//                    public void onError(Exception e) {
//                        ShowUtil.showToast(getApplicationContext(),e.getMessage());
//                    }
//                });
        Log.d("MainActivity", "onDestroy");
        HttpGet.getInstance().clear();
        MainAction.getInstance(MainActivity.this).clear();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {

            if (!meiZhi.isEmpty()){
                rlMeizhi.smoothScrollToPosition(meiZhi.size()-1);
            }
//            startActivity(new Intent(MainActivity.this, TestActivity.class));
            return true;
        }
        if (id==R.id.action_size){
            ShowUtil.showSnack(rlMeizhi,"当前造成的缓存："+GlideLoad.getInstance().getCacheSize(this));
        }
        if (id==R.id.action_clear){
            GlideLoad.getInstance().clearImageAllCache(this);
            ShowUtil.showSnack(rlMeizhi,"已清除缓存："+GlideLoad.getInstance().getCacheSize(MainActivity.this));
            adapter.notifyDataSetChanged();
        }
        if (id==R.id.action_autoload){
            Preference.getInstance().save(this,true);
            for (MeiZhi meiZhi1:meiZhi){
                meiZhi1.setShow(true);
            }
            adapter.notifyDataSetChanged();
        }
        if (id==R.id.action_notautoload){
            Preference.getInstance().save(this,false);
            for (MeiZhi meiZhi1:meiZhi){
                if (meiZhi1.isShow(this)==false){
                    meiZhi1.setShow(false);
                }
            }
            adapter.notifyDataSetChanged();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        rlMeizhi.smoothScrollToPosition(0);
        TwoToExit.getInstance().onBackPressed(this);
    }

}
