package com.example.lenovo.timescroller.Model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.Serializable;

/**
 * Created by chris on 15/12/14.
 */
public class BaseObject implements Serializable {

    public String toJson(){
        Gson gson = new GsonBuilder().disableHtmlEscaping().create();   //不转义html元素
        return gson.toJson(this);
    }

    @Override
    public String toString() {
        return toJson();
    }
}
