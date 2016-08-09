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
import android.view.MenuItem;

import com.example.lenovo.timescroller.Fragment.MainFragment;
import com.example.lenovo.timescroller.Fragment.MenuFragment;
import com.example.lenovo.timescroller.Fragment.SecondFragment;
import com.example.lenovo.timescroller.R;

public class MainActivity extends AppCompatActivity{


    Toolbar toolbar;
    DrawerLayout layout;
    ActionBarDrawerToggle drawerToggle;
    final Class<?> []tabFragments = {MainFragment.class, SecondFragment.class};
    FragmentManager fragmentManager;
    Fragment currentFragment;
    int currentIndex;

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
        drawerToggle = new ActionBarDrawerToggle(this,layout,toolbar,R.string.open,R.string.close);
        layout.setDrawerListener(drawerToggle);
        fragmentManager.beginTransaction().replace(R.id.main_drawer_fl,new MenuFragment()).commit();
        switchFragment(0);
    }

    private void switchFragment(int index){
        try {
            currentFragment = (Fragment) tabFragments[index].newInstance();
            fragmentManager.beginTransaction().replace(R.id.main_content,currentFragment).commit();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
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
}


