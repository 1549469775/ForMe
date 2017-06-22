package com.xieyaxin.space.forme.Util;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by John on 2017/2/21.
 */

public class JsonUtil {

    private static Gson mGson = new Gson();

    /**
     * 将json字符串转化成实体对象
     * @param json
     * @param classOfT
     * @return
     */
    public static Object stringToObject( String json , Class classOfT){
        return  mGson.fromJson( json , classOfT ) ;
    }

    // 将Json数据解析成相应的映射对象
    public static <T> T parseJsonWithGson(String jsonData, Class<T> type) {
        Gson gson = new Gson();
        T result = gson.fromJson(jsonData, type);
        return result;
    }

    /**
     * 将对象准换为json字符串 或者 把list 转化成json
     * @param object
     * @param <T>
     * @return
     */
    public static <T> String objectToString(T object) {
        return mGson.toJson(object);
    }

    /**
     * 把json 字符串转化成list
     * @param json
     * @param cls
     * @param <T>
     * @return
     */
    public static <T> List<T> stringToList(String json , Class<T> cls  ){
        Gson gson = new Gson();
        List<T> list = new ArrayList<T>();
        JsonArray array = new JsonParser().parse(json).getAsJsonArray();
        for(final JsonElement elem : array){
            list.add(gson.fromJson(elem, cls));
        }
        return list ;
    }

    public static boolean judgeError(String json){
        JSONObject jsonObject= null;
        boolean error=true;
        try {
            jsonObject = new JSONObject(json);
            error=jsonObject.getBoolean("error");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return error;
    }

    public static String getEntity(String json){
        JSONObject jsonObject= null;
        String response="";
        try {
            jsonObject = new JSONObject(json);
            response=jsonObject.getString("results");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return response;
    }
    public static String getString(String para,String json){
        JSONObject jsonObject= null;
        String message="";
        try {
            jsonObject = new JSONObject(json);
            message=jsonObject.getString(para);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return message;
    }

}
