package com.xieyaxin.space.forme.Cache;

import com.xieyaxin.space.forme.Bean.MeiZhi;

import java.io.Serializable;
import java.util.List;

/**
 * Created by John on 2017/4/23.
 */

public class CacheData implements Serializable {

    private long lastUpdated;
    private String key;
    private List data;
    private long expiration;

    public CacheData(String key,List data) {
        this(key,data,-1);
    }

    public CacheData(String key, List data, long expiration) {
        this.key = key;
        this.expiration = expiration;

    }

    public void setData(List data) {
        this.data = data;
        this.lastUpdated=System.currentTimeMillis();
    }

    public boolean isValid(){
        return expiration==-1||lastUpdated+expiration>System.currentTimeMillis();
    }

    public String getKey() {
        return key;
    }

    public List getData() {
        return data;
    }
}
