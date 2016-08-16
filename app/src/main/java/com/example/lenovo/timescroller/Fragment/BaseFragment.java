package com.example.lenovo.timescroller.Fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lenovo.timescroller.Inteferce.IUIControler;

import butterknife.ButterKnife;

/**
 * Created by kevin.tian on 2016/8/15.
 */
public abstract class BaseFragment extends Fragment implements IUIControler{


    private View mContentView;

    private Context mContext;

    private ProgressDialog mDialog;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContentView = inflater.inflate(setLayoutId(),container,false);
        mContext = getContext();
        initUI();
        initData();
        return mContentView;
    }

    protected View getContentView(){
        return mContentView;
    }

    protected Context getmContext(){
        return mContext;
    }

    protected <T extends View> T IFindViwById(int layoutId){
        return (T)mContentView.findViewById(layoutId);
    }

    protected abstract int setLayoutId();

    protected void startProgressDiolag(){
        if (mDialog == null){
            mDialog = new ProgressDialog(mContext);
            mDialog.setCanceledOnTouchOutside(false);
            mDialog.show();
        }
    }

    protected void stopProgressDiolag(){
        if (mDialog!=null){
            mDialog.dismiss();
            mDialog=null;
        }
    }

    @Override
    public void initUI() {
        ButterKnife.inject(this, getContentView());
    }

    @Override
    public void initData() {

    }
}
