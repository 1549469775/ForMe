/*
 * Copyright 2015 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.xieyaxin.space.forme.Util;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.util.Pair;
import android.view.View;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Helper class for creating content transitions used with {@link android.app.ActivityOptions}.
 */
public class TransitionHelper {

    /**創建活動過渡期間所需的轉換參與者
     * 同时避免系统的UI问题//如状态栏，导航栏
     * Create the transition participants required during a activity transition while
     * avoiding glitches with the system UI.
     *
     * @param activity The activity used as start for the transition.//该activity用作转换开始
     * @param includeStatusBar If false, the status bar will not be added as the transition//如果為false，則不會將狀態欄添加為轉換
     *        participant.
     * @return All transition participants.//所有轉換參與者。
     */
    public static Pair<View, String>[] createSafeTransitionParticipants(@NonNull Activity activity,
                                                          boolean includeStatusBar, @Nullable Pair... otherParticipants) {
        // Avoid system UI glitches as described here:避免系統UI故障如此處所述
        // https://plus.google.com/+AlexLockwood/posts/RPtwZ5nNebb
        View decor = activity.getWindow().getDecorView();
        //activity.getWindow()为檢索活動的當前窗口 .getDecorView()为檢索頂級窗口裝飾視圖（包含標準窗口框架/裝飾和其中的客戶端內容），可以將其作為窗口添加到窗口管理器。
        View statusBar = null;//状态栏
        if (includeStatusBar) {//转换时是否包含状态栏
            statusBar = decor.findViewById(android.R.id.statusBarBackground);//状态栏背景
        }
        View navBar = decor.findViewById(android.R.id.navigationBarBackground);//导航栏背景

        // Create pair of transition participants.創建一對轉換參與者
        List<Pair> participants = new ArrayList<>(3);
        addNonNullViewToTransitionParticipants(statusBar, participants);
        addNonNullViewToTransitionParticipants(navBar, participants);
        // only add transition participants if there's at least one none-null element
        if (otherParticipants != null && !(otherParticipants.length == 1//otherParticipants有至少一个数据
                && otherParticipants[0] == null)) {
            participants.addAll(Arrays.asList(otherParticipants));
        }
        return participants.toArray(new Pair[participants.size()]);//将list转化为你所需要类型的数组
    }

    //此函数以为当view为孔氏，不会添加装还参与者，就是只上面加了判断的状态栏
    private static void addNonNullViewToTransitionParticipants(View view, List<Pair> participants) {
        if (view == null) {
            return;
        }
        participants.add(new Pair<>(view, view.getTransitionName()));//交换元素的姓名
    }

}
