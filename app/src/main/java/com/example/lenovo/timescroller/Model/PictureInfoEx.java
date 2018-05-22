package com.example.lenovo.timescroller.Model;


/**
 * Created by chris on 16/3/15.
 */
public class PictureInfoEx extends BaseObject {

    private float progress = 0f;
    private String progressDesc = null;

    public float getProgress() {
        return progress;
    }

    public void setProgress(float progress) {
        this.progress = progress;
    }

    public String getProgressDesc() {
        return progressDesc;
    }

    public void setProgressDesc(String progressDesc) {
        this.progressDesc = progressDesc;
    }
}
