package com.xieyaxin.space.forme.Preference;

import android.content.Context;
import android.widget.TextView;

import com.xieyaxin.space.forme.Bean.MeiZhi;
import com.xieyaxin.space.forme.Http.HttpGet;
import com.xieyaxin.space.forme.Interface.ResultListener;

import java.util.List;

/**
 * Created by John on 2017/4/22.
 */

public class MainAction {

    private static MainAction action = null;

    private MainAction(Context context){
        this.context=context;
    }

    public static MainAction getInstance(Context context){
        if (action==null){
            synchronized (MainAction.class){
                if (action==null){
                    action=new MainAction(context);
                }
            }
        }
        return action;
    }

    private Context context;

    /**
     * 获取最新的页面
     * @param listener 判断并返回获取的结果
     */
    public void getDataFromNet(final ResultListener listener){
        HttpGet.getInstance()
                .get("http://gank.io/api/data/福利/10/1",new ResultListener() {
                    @Override
                    public void error(Exception e) {
                        listener.error(e);
                    }

                    @Override
                    public void result(Object object) {
                        listener.result(object);
                    }
                });
    }

    public void getMoreDataFromNet(int page,final ResultListener listener){
        HttpGet.getInstance()
                .get("http://gank.io/api/data/福利/10/"+page,new ResultListener() {
                    @Override
                    public void error(Exception e) {
                        listener.error(e);
                    }

                    @Override
                    public void result(Object object) {
                        listener.result(object);
                    }
                });
    }

    public void clear(){
        action=null;
        context=null;
    }

}
