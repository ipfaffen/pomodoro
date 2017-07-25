package com.ipfaffen.pomodoro.activity;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.ipfaffen.pomodoro.R;
import com.ipfaffen.pomodoro.fragment.FragmentBase;
import com.ipfaffen.pomodoro.fragment.HistoryFragment;
import com.ipfaffen.pomodoro.fragment.SettingsFragment;
import com.ipfaffen.pomodoro.fragment.TimerFragment;
import com.ipfaffen.pomodoro.listener.OnPageChangeSimpleListener;
import com.ipfaffen.pomodoro.util.PagerHelper;
import com.ipfaffen.pomodoro.util.PagerItem;
import com.ipfaffen.pomodoro.util.SmartFragmentStatePagerAdapter;

/**
 * @author Isaias Pfaffenseller
 */
public class MainActivity extends ActivityBase implements NavigationView.OnNavigationItemSelectedListener {

    private PagerHelper pagerHelper;
    private PagerAdapter pagerAdapter;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // Configure activity items/fragments.
        pagerHelper = new PagerHelper();
        pagerHelper.addItem(new PagerItem(0, R.id.nav_settings, R.string.menu_settings, SettingsFragment.class));
        pagerHelper.addItem(new PagerItem(1, R.id.nav_new, R.string.menu_new, TimerFragment.class), true);
        pagerHelper.addItem(new PagerItem(2, R.id.nav_history, R.string.menu_history, HistoryFragment.class));

        // Create the adapter that will return a fragment for each section of the activity.
        pagerAdapter = new PagerAdapter(getSupportFragmentManager());

        // Set up the view pager with the sections adapter.
        viewPager = (ViewPager) findViewById(R.id.container);
        viewPager.setAdapter(pagerAdapter);
        viewPager.setCurrentItem(pagerHelper.getMainItem().getPosition());
        viewPager.addOnPageChangeListener(new OnPageChangeSimpleListener() {
            @Override
            public void onPageSelected(int position) {
                FragmentBase fragment = (FragmentBase) pagerAdapter.getRegisteredFragment(position);
                fragment.prepare();
            }
        });

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if(drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
        else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem menuItem) {
        // Handle navigation view item clicks here.
        int navId = menuItem.getItemId();
        int pagerItemPosition = pagerHelper.getItemByNavId(navId).getPosition();
        if(pagerItemPosition >= 0) {
            viewPager.setCurrentItem(pagerItemPosition);
        }
        else if(navId == R.id.nav_share) {
            // TODO Implement share option...
            return false;
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /**
     * Adapter that returns a fragment corresponding to one of the sections/tabs/pages.
     */
    public class PagerAdapter extends SmartFragmentStatePagerAdapter {

        /**
         * @param fm
         */
        public PagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return pagerHelper.getItemByPosition(position).newFragmentInstance();
        }

        @Override
        public int getCount() {
            return pagerHelper.getTotalItems();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return getString(pagerHelper.getItemByPosition(position).getTitleResId());
        }
    }
}