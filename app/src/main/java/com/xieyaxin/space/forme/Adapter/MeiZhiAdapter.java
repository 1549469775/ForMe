package com.xieyaxin.space.forme.Adapter;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.xieyaxin.space.forme.Bean.MeiZhi;
import com.xieyaxin.space.forme.Interface.SuccessListener;
import com.xieyaxin.space.forme.R;
import com.xieyaxin.space.forme.Util.GlideLoad;
import com.xieyaxin.space.forme.Util.ShowUtil;

import java.util.List;

import butterknife.BindView;

/**
 * Created by John on 2017/4/22.
 */

public class MeiZhiAdapter extends RecyclerView.Adapter {
    private static final int VIEW_TOP=0;
    private static final int VIEW_FOOTER=1;

    private Context context;
    private List<MeiZhi> meiZhiList;

    private OnClickListener onClickListener;

    //当前滚动的position下面最小的items的临界值
    private int visibleThreshold = 5;
    private boolean isLoading;
    private int totalItemCount;
    private int lastVisibleItemPosition;

    public MeiZhiAdapter(Context context,List<MeiZhi> meiZhiList,RecyclerView recyclerView) {
        this.context = context;
        this.meiZhiList = meiZhiList;
        if (recyclerView.getLayoutManager() instanceof LinearLayoutManager) {
            final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
            //mRecyclerView添加滑动事件监听
            recyclerView.setHasFixedSize(true);
            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                    totalItemCount = linearLayoutManager.getItemCount();
                    lastVisibleItemPosition = linearLayoutManager.findLastVisibleItemPosition();
                    Log.d("test", "totalItemCount =" + totalItemCount + "-----" + "lastVisibleItemPosition =" + lastVisibleItemPosition);
                    if (!isLoading && totalItemCount <= (lastVisibleItemPosition + visibleThreshold)) {
                        //此时是刷新状态
                        if (mMoreDataListener != null)
                            mMoreDataListener.loadMoreData();
                        isLoading = true;
                    }
                }
            });
        }
    }

    @Override
    public int getItemViewType(int position) {
        int type;//
        type=meiZhiList.get(position)!=null?VIEW_TOP:VIEW_FOOTER;
        return type;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        RecyclerView.ViewHolder viewHolder = null;
        switch (viewType) {
            case VIEW_TOP:
                view = LayoutInflater.from(context).inflate(R.layout.item_meizhi, parent, false);
                viewHolder = new MeiZhiItemAdapter(view);
                break;
            case VIEW_FOOTER:
                view = LayoutInflater.from(context).inflate(R.layout.progressbar, parent, false);
                viewHolder = new MyProgressViewHolder(view);
                break;
        }

        return viewHolder;
    }

    /**
     * 在函数中判断了每个妹纸是否显示，同时点击了一定会显示，但是当数据更新时不知道数据会不会全部变成false
     * 关于点击后图片闪烁一下再跳转，估计是在那个activity中先读取内存再显示的缘故
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final RecyclerView.ViewHolder viewHolder=holder;
        if (viewHolder instanceof MeiZhiItemAdapter){
            if (meiZhiList.get(position).isShow(context)==true){
                GlideLoad.getInstance().loadImageFromNet(context,meiZhiList.get(position).getUrl(),((MeiZhiItemAdapter) viewHolder).imgMeizhi);
            }else {
                GlideLoad.getInstance().loadImagePlaceHolder(context,((MeiZhiItemAdapter) viewHolder).imgMeizhi);
            }
            ((MeiZhiItemAdapter) viewHolder).tvMeizhi.setText(meiZhiList.get(position).getDesc());
            ((MeiZhiItemAdapter) viewHolder).itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    if (!meiZhiList.get(position).isShow(context)){
                        GlideLoad.getInstance()
                                .loadImageFromNet(context, meiZhiList.get(position).getUrl(), ((MeiZhiItemAdapter) viewHolder).imgMeizhi,
                                        new SuccessListener() {
                                            @Override
                                            public void onSuccess() {
                                                meiZhiList.get(position).setShow(true);
                                                onClickListener.onClick(v,position);
                                            }
                                        });
                    }else {
                        onClickListener.onClick(v,position);
                    }
                }
            });
        }else if (viewHolder instanceof MyProgressViewHolder){
            if (((MyProgressViewHolder) viewHolder).pb != null)
                ((MyProgressViewHolder) viewHolder).pb.setIndeterminate(true);
        }
    }


    public void setLoaded(boolean loaded) {
        isLoading = loaded;
    }

    public interface LoadMoreDataListener {
         void loadMoreData();
    }

    private LoadMoreDataListener mMoreDataListener;

    //加载更多监听方法
    public void setOnMoreDataLoadListener(LoadMoreDataListener onMoreDataLoadListener) {
        mMoreDataListener = onMoreDataLoadListener;
    }

    @Override
    public int getItemCount() {
        return meiZhiList.size();
    }

    public interface OnClickListener {
        void onClick(View view,int position);
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public class MyProgressViewHolder extends RecyclerView.ViewHolder {

        private ProgressBar pb;

        public MyProgressViewHolder(View itemView) {
            super(itemView);
            pb = (ProgressBar) itemView.findViewById(R.id.pb);
        }

    }

    private class MeiZhiItemAdapter extends RecyclerView.ViewHolder implements View.OnTouchListener {

        ImageView imgMeizhi;
        TextView tvMeizhi;

        public MeiZhiItemAdapter(View itemView) {
            super(itemView);
            imgMeizhi= (ImageView) itemView.findViewById(R.id.img_meizhi);
            imgMeizhi.setColorFilter(Color.parseColor("#5e000000"));
            tvMeizhi= (TextView) itemView.findViewById(R.id.tv_meizhi);
            itemView.setOnTouchListener(this);
        }

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction()){
                case MotionEvent.ACTION_DOWN:
                    imgMeizhi.setColorFilter(null);
                    hide(tvMeizhi);
                    View parent= (View) itemView.getParent();//经测试，改id为recyclerView的ID，rl_meizhi
                    if (parent!=null){
                        parent.setOnTouchListener(this);
                    }
                    break;
                case MotionEvent.ACTION_UP://被item的点击事件覆盖了，监听不到,当return true时可以监听到
                    //此时的v的id为rl_meizhi,而不是tvMeizhi
                    imgMeizhi.setColorFilter(Color.parseColor("#5e000000"));
                    show(tvMeizhi);
                    break;
//                case MotionEvent.ACTION_CANCEL://焦点离开时   同时return true，便可以监听到了，但item的点击事件监听不到了
//                    //此时的v的id为rl_meizhi,而不是tvMeizhi
//                    imgMeizhi.setColorFilter(Color.parseColor("#5e000000"));
//                    show(tvMeizhi);
//                    break;
            }
            return false;
        }

        private void show(View v){
            ObjectAnimator alpha= ObjectAnimator.ofFloat(v,"alpha",0,1);
            alpha.setDuration(300);
            alpha.start();
        }
        private void hide(View v){
            ObjectAnimator alpha= ObjectAnimator.ofFloat(v,"alpha",1,0);
            alpha.setDuration(300);
            alpha.start();
        }
    }

}
