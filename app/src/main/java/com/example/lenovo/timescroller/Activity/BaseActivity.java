package com.example.lenovo.timescroller.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.lenovo.timescroller.Inteferce.IUIControler;
import com.example.lenovo.timescroller.R;

import butterknife.ButterKnife;

/**
 * Created by kevin.tian on 2016/8/15.
 */
public abstract class BaseActivity extends AppCompatActivity implements IUIControler {
    private Context mContext;
    protected Toolbar mToolbar;
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
        extra = getIntent().getStringExtra(OBJECT_EXTRA);
        mContext = this;
        initToolBar();
        initUI();
        initData();
    }

    private void initToolBar() {
        ButterKnife.inject(this);
        mToolbar = IFindViewByid(R.id.toolbars);
        if (mToolbar != null) {
            // mToolbar.setBackgroundColor(getResources().getColor(R.color.theme_color));
            // mToolbar.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.theme_color));
            //  mToolbar.setTitleTextAppearance(this, R.style.ToolBarTitleTextApperance);
            setSupportActionBar(mToolbar);
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
    public void initUI() {

    }

    @Override
    public void initData() {

    }
}
