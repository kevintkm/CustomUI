package com.example.lenovo.timescroller.Model;

import java.util.List;

/**
 * Created by kevin.tian on 2016/8/15.
 */
public class MeiZhi extends BaseModel{

    /**
     * error : false
     * results : [{"_id":"57ad49a6421aa94a0226e658","createdAt":"2016-08-12T11:59:34.857Z","desc":"张老师又来了，各位同学，北京机会多，北京钱多，但是不是你想那样... 哈哈哈哈哈 笑惨了！","publishedAt":"2016-08-15T11:27:22.462Z","source":"chrome","type":"休息视频","url":"http://weibo.com/p/23044483bb9d5cf510e4634012c3056a6c9105","used":true,"who":"代码家"},{"_id":"57ad41e4421aa94a077b3553","createdAt":"2016-08-12T11:26:28.497Z","desc":"爱酱小时候，边打球边哭，太可爱了～～","publishedAt":"2016-08-12T11:39:10.578Z","source":"chrome","type":"休息视频","url":"http://weibo.com/p/230444d389e174bf27572c56c38c4f1366137c","used":true,"who":"Allen"},{"_id":"5718ed8067765974f885bf4e","createdAt":"2016-04-21T23:10:56.276Z","desc":"俄罗斯小哥Oleg Cricket，这是他在迪拜某高楼拍的视频，近距离感受下什么叫战斗民族。。","publishedAt":"2016-08-11T12:07:01.963Z","source":"chrome","type":"休息视频","url":"http://weibo.com/p/230444a15ab25498ca6d014542cb3be5a55b4c","used":true,"who":"LHF"},{"_id":"57aa7e77421aa90b3aac1ee0","createdAt":"2016-08-10T09:08:07.150Z","desc":"林纳斯\u2022托瓦兹:Linux 操作系统之父","publishedAt":"2016-08-10T11:37:13.981Z","source":"web","type":"休息视频","url":"http://v.youku.com/v_show/id_XMTY1MjYyNjkyOA==.html","used":true,"who":"龙龙童鞋"},{"_id":"57a20a4b421aa91e2606477f","createdAt":"2016-08-03T23:14:19.12Z","desc":"有影射啊：假如买菜的流程很繁琐","publishedAt":"2016-08-09T11:30:16.672Z","source":"chrome","type":"休息视频","url":"http://weibo.com/p/2304449bc13898b750fcc8e876a3c1a80920a0","used":true,"who":"lxxself"},{"_id":"57a7df16421aa90b35e1f3eb","createdAt":"2016-08-08T09:23:34.895Z","desc":"白岩松的对开幕式的吐槽解说精华合辑版！你们随意感受下这位国家级段子手。。。全程笑点","publishedAt":"2016-08-08T11:47:45.839Z","source":"chrome","type":"休息视频","url":"http://www.miaopai.com/show/cF8q9ntjKeV88LSnvTjnyA__.htm","used":true,"who":"lxxself"},{"_id":"579c197c421aa90d36e960bf","createdAt":"2016-07-30T11:05:32.987Z","desc":"有个好莱坞做特效的爸爸是一种什么样的体验？ 卧槽简直吊炸天，最后一个我下巴都跟着掉了！","publishedAt":"2016-08-05T11:31:58.293Z","source":"chrome","type":"休息视频","url":"http://weibo.com/p/230444058a763da0c7ddff016823859a9b38ed","used":true,"who":"lxxself"},{"_id":"57a149dd421aa90d39e709cb","createdAt":"2016-08-03T09:33:17.875Z","desc":"听着这些歌，才发现20年过的如此之快啊","publishedAt":"2016-08-03T11:12:47.159Z","source":"chrome","type":"休息视频","url":"http://www.miaopai.com/show/1Gy3JMSJUjd~6Gvbmq-uuw__.htm","used":true,"who":"lxxself"},{"_id":"579d8465421aa90d36e960c5","createdAt":"2016-07-31T12:53:57.530Z","desc":"暖心忘年交《善意》","publishedAt":"2016-08-02T11:40:01.363Z","source":"chrome","type":"休息视频","url":"http://v.youku.com/v_show/id_XMTY1MzkzMjQyNA==.html","used":true,"who":"lxxself"},{"_id":"579ea6d0421aa91e26064746","createdAt":"2016-08-01T09:33:04.931Z","desc":"哈哈哈好污[哈哈]这个老师可能是性 冷淡","publishedAt":"2016-08-01T12:00:57.45Z","source":"chrome","type":"休息视频","url":"http://www.miaopai.com/show/HrBuGCzGZ53UgCaZoNWahA__.htm","used":true,"who":"lxxself"}]
     */

    private boolean error;
    /**
     * _id : 57ad49a6421aa94a0226e658
     * createdAt : 2016-08-12T11:59:34.857Z
     * desc : 张老师又来了，各位同学，北京机会多，北京钱多，但是不是你想那样... 哈哈哈哈哈 笑惨了！
     * publishedAt : 2016-08-15T11:27:22.462Z
     * source : chrome
     * type : 休息视频
     * url : http://weibo.com/p/23044483bb9d5cf510e4634012c3056a6c9105
     * used : true
     * who : 代码家
     */

    private List<ResultsBean> results;

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public List<ResultsBean> getResults() {
        return results;
    }

    public void setResults(List<ResultsBean> results) {
        this.results = results;
    }

    public static class ResultsBean {
        private String _id;
        private String createdAt;
        private String desc;
        private String publishedAt;
        private String source;
        private String type;
        private String url;
        private boolean used;
        private String who;

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
    }
}
