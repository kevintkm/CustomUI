package com.example.lenovo.timescroller.Activity;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import com.example.lenovo.timescroller.Fragment.MainFragment;
import com.example.lenovo.timescroller.Fragment.MenuFragment;
import com.example.lenovo.timescroller.Fragment.SecondFragment;
import com.example.lenovo.timescroller.Fragment.ThirdFragment;
import com.example.lenovo.timescroller.R;
import com.example.lenovo.timescroller.ViewHolder.MenuViewHolder;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements MenuViewHolder.MenuItemClickListener {


    Toolbar toolbar;
    DrawerLayout layout;
    ActionBarDrawerToggle drawerToggle;
    final Class<?>[] tabFragments = {MainFragment.class, SecondFragment.class , ThirdFragment.class};
    FragmentManager fragmentManager;
    Fragment currentFragment;
    Fragment menuFragment;
    ArrayList<String> mDatas;
    HashMap<Integer, Fragment> fragmentHashMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initUI();
    }

    private void initUI() {
        fragmentManager = getSupportFragmentManager();
        toolbar = (Toolbar) findViewById(R.id.toolbars);
        layout = (DrawerLayout) findViewById(R.id.drawer);
        drawerToggle = new ActionBarDrawerToggle(this, layout, toolbar, R.string.open, R.string.close);
        layout.setDrawerListener(drawerToggle);
        mDatas = new ArrayList<>();
        mDatas.add("首页");
        mDatas.add("新闻");
        mDatas.add("aaaa");
        menuFragment = new MenuFragment();
        Bundle bundle = new Bundle();
        bundle.putStringArrayList("data", mDatas);
        menuFragment.setArguments(bundle);

        fragmentHashMap = new HashMap<>();

        fragmentManager.beginTransaction().replace(R.id.main_drawer_fl, menuFragment).commit();
        currentFragment = getMenuFragment(0);
        fragmentManager.beginTransaction().add(R.id.main_content, currentFragment).commit();
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
            if (fragment==null){
                fragment = (Fragment) tabFragments[index].newInstance();
                fragmentHashMap.put(index,fragment);
            }
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return fragment;
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
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
            switchFragment(mDatas.indexOf(object));
            Log.d("========", "ViewHolder           Click");
        }
    }

}


