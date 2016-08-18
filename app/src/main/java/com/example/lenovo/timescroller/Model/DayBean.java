package com.example.lenovo.timescroller.Model;

import java.util.List;

/**
 * Created by kevin.tian on 2016/8/18.
 */
public class DayBean {

    private List<String> category;

    private boolean error;

    private Result results;

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public List<String> getCategory() {
        return category;
    }

    public void setCategory(List<String> category) {
        this.category = category;
    }

    public Result getResults() {
        return results;
    }

    public void setResults(Result results) {
        this.results = results;
    }

    public class Result {
        private List<MeiZhi.ResultsBean> Android;
        private List<MeiZhi.ResultsBean> iOS;
        private List<MeiZhi.ResultsBean> 拓展资源;
        private List<MeiZhi.ResultsBean> 休息视频;
        private List<MeiZhi.ResultsBean> 瞎推荐;
        private List<MeiZhi.ResultsBean> 福利;

        public List<MeiZhi.ResultsBean> getAndroid() {
            return Android;
        }

        public void setAndroid(List<MeiZhi.ResultsBean> android) {
            Android = android;
        }

        public List<MeiZhi.ResultsBean> get瞎推荐() {
            return 瞎推荐;
        }

        public void set瞎推荐(List<MeiZhi.ResultsBean> 瞎推荐) {
            this.瞎推荐 = 瞎推荐;
        }

        public List<MeiZhi.ResultsBean> get休息视频() {
            return 休息视频;
        }

        public void set休息视频(List<MeiZhi.ResultsBean> 休息视频) {
            this.休息视频 = 休息视频;
        }

        public List<MeiZhi.ResultsBean> get拓展资源() {
            return 拓展资源;
        }

        public void set拓展资源(List<MeiZhi.ResultsBean> 拓展资源) {
            this.拓展资源 = 拓展资源;
        }

        public List<MeiZhi.ResultsBean> getiOS() {
            return iOS;
        }

        public void setiOS(List<MeiZhi.ResultsBean> iOS) {
            this.iOS = iOS;
        }

        public List<MeiZhi.ResultsBean> get福利() {
            return 福利;
        }

        public void set福利(List<MeiZhi.ResultsBean> 福利) {
            this.福利 = 福利;
        }
    }


}
