package com.xieyaxin.space.forme.Util;

import android.text.TextUtils;

import com.xieyaxin.space.forme.Bean.MeiZhi;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by John on 2017/4/22.
 */

public class RelpaceUtil {

    public static void combine(List<MeiZhi> shortList, List<MeiZhi> longList){
        remove(shortList,longList);
    }

    private static void showAll(List<MeiZhi> list){
        for (MeiZhi m:list){
            ShowUtil.showLog("Log",m.getDesc()+"");
        }
        ShowUtil.showLog("Log","-------------------------------------------------");
    }
    //以下代码为判断两个列表中重合的元素，将重复元素删掉
    private static void remove(List<MeiZhi> shortList, List<MeiZhi> longList){
        for (int i = shortList.size()-1; i >= 0; i--) {
            for (int j = longList.size()-1; j>= 0; j--) {
                if (TextUtils.equals(shortList.get(i).get_id(),longList.get(j).get_id())){
                    shortList.remove(i);
                    i--;//当remove后，大小会减小
                }
            }
        }
        longList.addAll(0,shortList);
    }

}
