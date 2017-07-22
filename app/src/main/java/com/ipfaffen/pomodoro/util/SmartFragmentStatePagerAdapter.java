package com.ipfaffen.pomodoro.util;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.SparseArray;
import android.view.ViewGroup;

/**
 * Extension of FragmentStatePagerAdapter which intelligently caches
 * all active fragments and manages the fragment lifecycles.
 * Usage involves extending from SmartFragmentStatePagerAdapter as you would any other PagerAdapter.
 *
 * @author https://gist.github.com/nesquena/c715c9b22fb873b1d259
 */
public abstract class SmartFragmentStatePagerAdapter extends FragmentStatePagerAdapter {

    // Sparse array to keep track of registered fragments in memory.
    private SparseArray<Fragment> registeredFragments = new SparseArray<Fragment>();

    /**
     * @param fragmentManager
     */
    public SmartFragmentStatePagerAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        // Register the fragment when the item is instantiated.
        Fragment fragment = (Fragment) super.instantiateItem(container, position);
        registeredFragments.put(position, fragment);
        return fragment;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        // Unregister when the item is inactive.
        registeredFragments.remove(position);
        super.destroyItem(container, position, object);
    }

    /**
     * Returns the fragment for the position (if instantiated).
     *
     * @param position
     * @return
     */
    public Fragment getRegisteredFragment(int position) {
        return registeredFragments.get(position);
    }
}