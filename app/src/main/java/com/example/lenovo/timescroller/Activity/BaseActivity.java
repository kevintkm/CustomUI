package com.example.lenovo.timescroller.Activity;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.example.lenovo.timescroller.Inteferce.IUIControler;
import com.example.lenovo.timescroller.R;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by kevin.tian on 2016/8/15.
 */
public abstract class BaseActivity extends AppCompatActivity implements IUIControler {
    private Context mContext;
    private Application application;
    @InjectView(R.id.toolbars)
    Toolbar mToolbar;
    private String title;
    protected String extra;
    public static final String OBJECT_EXTRA="extra";

    public static void startActivity(Context context, Class<?> activity,String text) {
        Intent intent = new Intent(context, activity);
        intent.putExtra(OBJECT_EXTRA,text);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(setLayoutId());
        ButterKnife.inject(this);
        Log.d("-------------Create",this.getClass().getName());
        extra = getIntent().getStringExtra(OBJECT_EXTRA);
        application = getApplication();
        mContext = this;
        initToolBar();
        initUI();
        initData();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("-------------Start",this.getClass().getName());
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("-------------Resume",this.getClass().getName());
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("-------------Pause",this.getClass().getName());
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        Log.d("-----AttachedToWindow",this.getClass().getName());
    }

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        Log.d("---------DetachedWindow",this.getClass().getName());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("-------------Destroy",this.getClass().getName());
    }

    protected void initToolBar() {
        if (mToolbar != null) {
            // mToolbar.setBackgroundColor(getResources().getColor(R.color.theme_color));
            // mToolbar.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.theme_color));
            //  mToolbar.setTitleTextAppearance(this, R.style.ToolBarTitleTextApperance);
            setSupportActionBar(mToolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });
        }
    }

    public void setToolBarTitle(String title) {
        this.title = title;
        if (getSupportActionBar() != null)
            getSupportActionBar().setTitle(title);
    }

    public String getToolBarTitle() {
        return title;
    }

    protected <T extends View> T IFindViewByid(int layoutId) {
        return (T) findViewById(layoutId);
    }

    protected abstract int setLayoutId();

    @Override
    public abstract void initUI() ;

    @Override
    public abstract void initData();
}
