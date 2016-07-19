package com.jyd.defaultdemos.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.jyd.defaultdemos.R;
import com.jyd.defaultdemos.ui.activity.BaseActivity;
import com.jyd.defaultdemos.ui.fragment.CartAnimFragment;
import com.jyd.defaultdemos.ui.fragment.LoadMoreTestFragment;
import com.jyd.defaultdemos.ui.fragment.ViewTestFragment;

import java.text.ParseException;

public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {

    private Toolbar toolbar;
    private DrawerLayout drawer ;
    private ActionBarDrawerToggle toggle ;
    private NavigationView navigationView ;
    public FloatingActionButton fab;
    private ViewPager viewPager;
    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        fab = getViewById(R.id.fab);
        viewPager = getViewById(R.id.viewpager_main);
    }

    @Override
    protected void setListener() {
        navigationView.setNavigationItemSelectedListener(this);
        fab.setOnClickListener(this);
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) throws ParseException {
        setViewPager();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_cart_anim) {
            // Handle the camera action
            viewPager.setCurrentItem(0);

        } else if (id == R.id.nav_share) {
            viewPager.setCurrentItem(1);
        } else if (id == R.id.nav_send) {
            viewPager.setCurrentItem(2);
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void setViewPager() {
        viewPager.setAdapter(new MainPagerAdapter(this, getSupportFragmentManager()) );
    }

    class MainPagerAdapter extends FragmentPagerAdapter{

        private Context mContext;
        private Class[] fragments = new Class[] {CartAnimFragment.class, ViewTestFragment.class, LoadMoreTestFragment.class};

        public MainPagerAdapter(Context context, FragmentManager fm) {
            super(fm);
            this.mContext = context;
        }

        @Override
        public Fragment getItem(int position) {
            return Fragment.instantiate(mContext, fragments[position].getName());
        }

        @Override
        public int getCount() {
            return fragments.length;
        }
    }
}
