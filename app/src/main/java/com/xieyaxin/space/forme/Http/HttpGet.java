package com.xieyaxin.space.forme.Http;

import com.xieyaxin.space.forme.Bean.MeiZhi;
import com.xieyaxin.space.forme.Interface.ResultListener;
import com.xieyaxin.space.forme.Util.JsonUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.builder.GetBuilder;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

/**
 * Created by John on 2017/4/21.
 */

public class HttpGet {

    private static HttpGet httpGet = null;
    private GetBuilder getBuilder=OkHttpUtils.get();

    private HttpGet(){}

    public static HttpGet getInstance(){
        if (httpGet==null){
            synchronized (HttpGet.class){
                if (httpGet==null){
                    httpGet=new HttpGet();
                }
            }
        }
        return httpGet;
    }

    public void get(String url,final ResultListener listener){
       getBuilder.url(url)
               .build()
               .execute(new StringCallback() {
                   @Override
                   public void onError(Call call, Exception e, int id) {
                       if (e!=null){
                           listener.error(e);
                       }
                   }

                   @Override
                   public void onResponse(String response, int id) {
                       if (!JsonUtil.judgeError(response)) {
                           listener.result(JsonUtil.stringToList(JsonUtil.getEntity(response),MeiZhi.class));
                       }else {
                           listener.error(new Exception("出错了"));
                       }
                   }
               });
    }

    public void clear(){
        httpGet=null;
        getBuilder=null;
    }

}
