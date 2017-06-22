package com.xieyaxin.space.forme.Util;

import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.text.style.URLSpan;
import android.view.View;
import android.widget.TextView;

import com.xieyaxin.space.forme.Interface.OnClickListener;
import com.xieyaxin.space.forme.MainActivity;

/**
 * 参考网址：存于平板默认浏览器书签中
 * 搜索TextView判断网址
 * TextView设置android:autoLink="web时可用
 * Created by John on 2017/4/22.
 */

public class TextWebUtil {

    private static TextWebUtil textWebUtil = null;

    private TextWebUtil(){}

    public static TextWebUtil getInstance() {
        if (textWebUtil==null){
            synchronized (TextWebUtil.class){
                if (textWebUtil==null){
                    textWebUtil = new TextWebUtil();
                }
            }
        }
        return textWebUtil;
    }

    private TextView tvResult;

    public void webLinkInTv(TextView tvResult){
        this.tvResult=tvResult;
        CharSequence text=tvResult.getText();
        if (text instanceof Spannable){
            int end =text.length();
            Spannable sp= (Spannable) text;
            URLSpan urls[]=sp.getSpans(0,end,URLSpan.class);
            SpannableStringBuilder style=new SpannableStringBuilder(text);
            style.clearSpans();
            for (URLSpan urlSpan:urls){
                MyURLSpan myURLSpan=new MyURLSpan(urlSpan.getURL());
//                style.setSpan(myURLSpan,sp.getSpanStart(urlSpan),sp.getSpanEnd(urlSpan),Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
                style.setSpan(myURLSpan,sp.getSpanStart(urlSpan),sp.getSpanEnd(urlSpan),Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
            }
            tvResult.setText(style);
        }
    }

    private class MyURLSpan extends ClickableSpan {
        private String url;
        public MyURLSpan(String url){
            this.url=url;
        }

        @Override
        public void updateDrawState(TextPaint ds) {
            super.updateDrawState(ds);
            ds.setUnderlineText(false);//去掉下划线
        }

        @Override
        public void onClick(View widget) {

        }
    }

    public void clear(){
        textWebUtil=null;
        tvResult=null;
    }
}
