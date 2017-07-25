package com.ipfaffen.pomodoro.util;

import android.util.Log;

import com.ipfaffen.pomodoro.fragment.FragmentBase;

/**
 * @author Isaias Pfaffenseller
 */
public class PagerItem  {

    private int position;
    private int navId;
    private int titleResId;
    private Class<? extends FragmentBase> fragmentClass;

    public PagerItem(int position, int navId, int titleResId, Class<? extends FragmentBase> fragmentClass) {
        this.position = position;
        this.navId = navId;
        this.titleResId = titleResId;
        this.fragmentClass = fragmentClass;
    }

    public int getPosition() {
        return position;
    }

    public int getNavId() {
        return navId;
    }

    public int getTitleResId() {
        return titleResId;
    }

    public Class<? extends FragmentBase> getFragmentClass() {
        return fragmentClass;
    }

    public FragmentBase newFragmentInstance() {
        if(fragmentClass == null) {
            return null;
        }
        try {
            return (FragmentBase) fragmentClass.newInstance();
        }
        catch(Exception e) {
            Log.e(PagerItem.class.getSimpleName(), e.getMessage(), e);
        }
        return null;
    }
}