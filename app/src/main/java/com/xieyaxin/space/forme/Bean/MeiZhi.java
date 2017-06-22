package com.xieyaxin.space.forme.Bean;

import android.content.Context;
import android.util.Log;

import java.util.List;

/**
 * Created by John on 2017/4/21.
 */

public class MeiZhi {
        /**
         * _id : 58f95b74421aa954511ebedf
         * createdAt : 2017-04-21T09:08:04.293Z
         * desc : 4-21
         * publishedAt : 2017-04-21T11:30:29.323Z
         * source : chrome
         * type : 福利
         * url : http://7xi8d6.com1.z0.glb.clouddn.com/2017-04-21-18013964_1389732981073150_4044061109068496896_n.jpg
         * used : true
         * who : daimajia
         */

        private String _id;
        private String createdAt;
        private String desc;
        private String publishedAt;
        private String source;
        private String type;
        private String url;
        private boolean used;
        private String who;
    private boolean isShow;

    public String get_id() {
            return _id;
        }

        public void set_id(String _id) {
            this._id = _id;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public String getPublishedAt() {
            return publishedAt;
        }

        public void setPublishedAt(String publishedAt) {
            this.publishedAt = publishedAt;
        }

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public boolean isUsed() {
            return used;
        }

        public void setUsed(boolean used) {
            this.used = used;
        }

        public String getWho() {
            return who;
        }

        public void setWho(String who) {
            this.who = who;
        }

        private boolean isFirst=true;
    public boolean isShow(Context context) {
        if (isFirst){
            isFirst=!isFirst;
            isShow=Preference.getInstance().getIsLoad(context);
        }
        return isShow;
    }

    public void setShow(boolean show) {
        isShow = show;
    }
}
