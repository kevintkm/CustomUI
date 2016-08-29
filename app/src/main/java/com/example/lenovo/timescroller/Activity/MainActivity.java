package com.example.lenovo.timescroller.Activity;

import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.example.lenovo.timescroller.Adapter.MenuAdapter;
import com.example.lenovo.timescroller.Fragment.AboutUserFragment;
import com.example.lenovo.timescroller.Fragment.DesignPatternFragment;
import com.example.lenovo.timescroller.Fragment.GankFragment;
import com.example.lenovo.timescroller.Fragment.MoudleLearningFragment;
import com.example.lenovo.timescroller.Fragment.ProjectLearningFragment;
import com.example.lenovo.timescroller.Fragment.MenuFragment;
import com.example.lenovo.timescroller.Fragment.CommonWidgetFragment;
import com.example.lenovo.timescroller.Fragment.SourceAnalyseFragment;
import com.example.lenovo.timescroller.Model.MenuBean;
import com.example.lenovo.timescroller.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends BaseActivity implements MenuAdapter.MenuItemClickListener {


    DrawerLayout layout;
    ActionBarDrawerToggle drawerToggle;
    final Class<?>[] tabFragments = {CommonWidgetFragment.class, ProjectLearningFragment.class, SourceAnalyseFragment.class, MoudleLearningFragment.class,
            DesignPatternFragment.class, AboutUserFragment.class};
    FragmentManager fragmentManager;
    Fragment currentFragment;
    Fragment menuFragment;
    ArrayList<MenuBean> mDatas;
    HashMap<Integer, Fragment> fragmentHashMap;

    @Override
    protected int setLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initUI() {
        fragmentManager = getSupportFragmentManager();
        layout = IFindViewByid(R.id.drawer);
        drawerToggle = new ActionBarDrawerToggle(this, layout, mToolbar, R.string.open, R.string.close);
        drawerToggle.syncState();
        layout.setDrawerListener(drawerToggle);
        mDatas = new ArrayList<>();
        AssetManager manager = getAssets();
        try {
            InputStream inputStream = manager.open("menu list.txt");
            BufferedReader buffer = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
            Type type = new TypeToken<List<MenuBean>>() {
            }.getType();
            Gson gson = new Gson();
            mDatas = gson.fromJson(buffer, type);
            setToolBarTitle(mDatas.get(0).getTitle());
        } catch (IOException e) {
            e.printStackTrace();
        }
        menuFragment = new MenuFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("data", mDatas);
        menuFragment.setArguments(bundle);
        fragmentHashMap = new HashMap<>();
        setToolBarTitle(mDatas.get(0).getTitle());
        fragmentManager.beginTransaction().replace(R.id.main_drawer_fl, menuFragment).commit();

        currentFragment = getMenuFragment(0);
        fragmentManager.beginTransaction().add(R.id.main_content, currentFragment).commit();
    }

    @Override
    public void initData() {

    }

    private void switchFragment(int index) {
        Fragment fragment = getMenuFragment(index);
        if (fragment.isAdded()) {
            fragmentManager.beginTransaction().hide(currentFragment).show(fragment).commitAllowingStateLoss();
        } else {
            fragmentManager.beginTransaction().hide(currentFragment).add(R.id.main_content, fragment).commitAllowingStateLoss();
        }
        currentFragment = fragment;
        layout.closeDrawers();

    }

    private Fragment getMenuFragment(int index) {
        Fragment fragment = null;
        try {
            fragment = fragmentHashMap.get(index);
            if (fragment == null) {
                fragment = (Fragment) tabFragments[index].newInstance();
                fragmentHashMap.put(index, fragment);
            }
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return fragment;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public void menuItemClick(Object object) {
        if (mDatas.contains(object)) {
            MenuBean bean = (MenuBean) object;
            setToolBarTitle(bean.getTitle());
            switchFragment(mDatas.indexOf(object));
        }
    }

}


