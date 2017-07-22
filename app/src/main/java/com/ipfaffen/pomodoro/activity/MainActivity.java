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
import com.ipfaffen.pomodoro.fragment.HistoryFragment;
import com.ipfaffen.pomodoro.fragment.SettingsFragment;
import com.ipfaffen.pomodoro.fragment.TimerFragment;
import com.ipfaffen.pomodoro.util.SmartFragmentStatePagerAdapter;

/**
 * @author Isaias Pfaffenseller
 */
public class MainActivity extends ActivityBase implements NavigationView.OnNavigationItemSelectedListener {

    private static final int PAGES = 3;
    private static final int PAGE_SETTINGS = 0;
    private static final int PAGE_TIMER = 1;
    private static final int PAGE_HISTORY = 2;

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

        // Create the adapter that will return a fragment for each section of the activity.
        pagerAdapter = new PagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        viewPager = (ViewPager) findViewById(R.id.container);
        viewPager.setAdapter(pagerAdapter);
        viewPager.setCurrentItem(PAGE_TIMER);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                if(position == PAGE_SETTINGS) {
                    SettingsFragment fragment = (SettingsFragment) pagerAdapter.getRegisteredFragment(position);
                    fragment.prepare();
                }
                else if(position == PAGE_TIMER) {
                    TimerFragment fragment = (TimerFragment) pagerAdapter.getRegisteredFragment(position);
                    fragment.prepare();
                }
                else if(position == PAGE_HISTORY) {
                    HistoryFragment fragment = (HistoryFragment) pagerAdapter.getRegisteredFragment(position);
                    fragment.list();
                }
            }

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {
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
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if(id == R.id.nav_new) {
            viewPager.setCurrentItem(1);
        }
        else if(id == R.id.nav_history) {
            viewPager.setCurrentItem(2);
        }
        else if(id == R.id.nav_settings) {
            viewPager.setCurrentItem(0);
        }
        else if(id == R.id.nav_share) {
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
            Fragment fragment =null;
            switch(position) {
                case PAGE_SETTINGS:
                    fragment = SettingsFragment.newInstance();
                    break;
                case PAGE_TIMER:
                    fragment = TimerFragment.newInstance();
                    break;
                case PAGE_HISTORY:
                    fragment = HistoryFragment.newInstance();
                    break;
            }
            return fragment;
        }

        @Override
        public int getCount() {
            return PAGES;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch(position) {
                case 0:
                    return getString(R.string.menu_settings);
                case 1:
                    return getString(R.string.menu_new);
                case 2:
                    return getString(R.string.menu_history);
            }
            return null;
        }
    }
}